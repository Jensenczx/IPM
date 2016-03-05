package com.example.chenjensen.ipm.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chenjensen.ipm.base.MyApplication;
import com.example.chenjensen.ipm.io.DiskLruCache;
import com.example.chenjensen.ipm.util.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by chenjensen on 16/3/4.
 */
public class ImageLoader {

    //some info of the Threadpool
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT+1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT*2+1;
    private static final long KEEP_ALIVE = 10L;
    //some parameters of the IO
    private static final long DISK_CACHE_SIZE = 1024*1024*50;
    private static final int DISK_CACHE_INDEX = 0;
    private static final int IO_BUFFER_SIZE = 8*1024;
    //handler
    private static final int MESSAGE_POST_RESULT = 1;
    //To do
    private static final int TAG_KEY_URI = 1;
    private static final String TAG = "ImageLoader";

    //线程工厂
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        //防止并发时，值出现问题，java提供的一个原子操作的类
        private final AtomicInteger mCount = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r,"ImageLoader#"+mCount.getAndIncrement());
        }
    };

    //线程池
    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE,MAXIMUM_POOL_SIZE,KEEP_ALIVE,
            TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(),sThreadFactory);

    //  handler实现交互
    private Handler mMainHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            LoaderResult result = (LoaderResult)msg.obj;
            ImageView imageView = result.imageView;
            //imageView.setImageBitmap(result.bitmap);
            String uri = (String) imageView.getTag();
            if (uri.equals(result.uri)) {
                imageView.setImageBitmap(result.bitmap);
            }else{
                //set a default background
            }
        }
    };

    private LruCache<String, Bitmap> mMemoryCache;
    private DiskLruCache mDiskLruCache;
    private Context mContext;
    private BitmapHelper mBitmapHelper;
    private boolean mIsDiskLruCacheCreated = false;

    //初始化二级缓存区
    private ImageLoader() {
        mContext = MyApplication.getContext();
        int maxMemorry = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemorry / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
        File diskCacheDir = getDiskCacheDir(mContext, "bitmap");
        if (!diskCacheDir.exists())
            diskCacheDir.mkdirs();
        if (getUsableSpace(diskCacheDir)>DISK_CACHE_SIZE){
            try{
                mDiskLruCache = DiskLruCache.open(diskCacheDir,1,1,DISK_CACHE_SIZE);
                mIsDiskLruCacheCreated = true;
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        mBitmapHelper = new BitmapHelper();
    }

    //创建loader实例
    public static ImageLoader build(){
        return new ImageLoader();
    }

    //加载bitmap,先从内存缓冲区，然后是从磁盘缓冲区，都没有的时候从网络进行加载
    public Bitmap loadBitmap(String uri,int reqWidth,int reqHeight){
        Bitmap bitmap = loadBitmapFromMemCache(uri);
        if(bitmap!=null)
            return bitmap;
        try{
            bitmap = loadBitmapFromDiskCache(uri,reqWidth,reqHeight);
            if(bitmap!=null)
                return bitmap;
            bitmap = loadBitmapFromHttp(uri,reqWidth,reqHeight);
        }catch (IOException e){
            e.printStackTrace();
        }
        if (bitmap == null && mIsDiskLruCacheCreated) {
            bitmap = downloadBitmapFromUrl(uri);
        }
        return bitmap;
    }

    //实现异步加载
    public void bindBitmap(final String uri,final ImageView imageView,final int reqWidth,final int reqHeight){
        imageView.setTag(uri);
        Bitmap bitmap = loadBitmapFromMemCache(uri);
        if(bitmap!=null){
            imageView.setImageBitmap(bitmap);
            return;
        }
        Runnable loadBitmapTask = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = loadBitmap(uri,reqWidth,reqHeight);
                if(bitmap!=null){
                    LoaderResult result = new LoaderResult(imageView,uri,bitmap);
                    //get the message from messge pool avoid to create new message
                    mMainHandler.obtainMessage(MESSAGE_POST_RESULT,result).sendToTarget();
                }
            }
        };
        THREAD_POOL_EXECUTOR.execute(loadBitmapTask);
    }

    //从内存缓冲区中加载
    private Bitmap loadBitmapFromMemCache(String url){
        final String key = hashKeyFormUrl(url);
        Bitmap bitmap = getBitmapFromMemCache(key);
        return bitmap;
    }

    //从网络加载，先将其存到磁盘，然后从磁盘中读出
    private Bitmap loadBitmapFromHttp(String url,int reqWidth,int reqHeight) throws IOException{
        if(Looper.myLooper()==Looper.getMainLooper()){
            throw new RuntimeException("can not visit network from UI thread");
        }
        if(mDiskLruCache == null){
            return null;
        }
        String key = hashKeyFormUrl(url);
        DiskLruCache.Editor editor = mDiskLruCache.edit(key);
        if(editor!=null){
            OutputStream outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
            if(downloadUrlToStream(url,outputStream)){
                editor.commit();
            }else{
                editor.abort();
            }
            mDiskLruCache.flush();
        }
        return loadBitmapFromDiskCache(url,reqWidth,reqHeight);
    }

    //从磁盘中读出图片
    private Bitmap loadBitmapFromDiskCache(String url,int reqWidth,int reqHeight) throws IOException{
        if(Looper.myLooper()==Looper.getMainLooper()){
            Log.w(TAG,"load bitmap from the UI Thread is not recommended!");
        }
        if(mDiskLruCache ==null){
            return null;
        }
        Bitmap bitmap = null;
        String key = hashKeyFormUrl(url);
        DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
        if(snapshot!=null){
            FileInputStream fileInputStream = (FileInputStream)snapshot.getInputStream(DISK_CACHE_INDEX);
            FileDescriptor fileDescriptor = fileInputStream.getFD();
            bitmap = mBitmapHelper.decodeBitmapFromFileDescriptor(fileDescriptor,reqWidth,reqHeight);
            if(bitmap!=null){
                addBitmapToMemoryCache(key,bitmap);
            }
        }
        return bitmap;
    }
    //将网络流转化为数据流
    public boolean downloadUrlToStream(String urlString,OutputStream outputStream){
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try{
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection)url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(),IO_BUFFER_SIZE);
            out = new BufferedOutputStream(outputStream,IO_BUFFER_SIZE);
            int b;
            while((b=in.read())!=-1){
                out.write(b);
            }
            return true;
        }catch (IOException e){
            Log.e(TAG,"downloadBitmap failed"+e);
        }finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            IOUtils.close(out);
            IOUtils.close(in);
        }
        return false;
    }
    //根据Url下载图片
    private Bitmap downloadBitmapFromUrl(String urlString){
        Bitmap bitmap = null;
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;
        try{
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(),IO_BUFFER_SIZE);
            bitmap = BitmapFactory.decodeStream(in);
        }catch (final IOException e){

        }finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            IOUtils.close(in);
        }
        return bitmap;
    }
    //添加图片到内存缓冲区
    private void addBitmapToMemoryCache(String key,Bitmap bitmap){
        if(getBitmapFromMemCache(key)==null)
            mMemoryCache.put(key,bitmap);
    }
    //从内存缓冲区拿到图片
    private Bitmap getBitmapFromMemCache(String key){
        return mMemoryCache.get(key);
    }
    //获取缓冲区目录
    public File getDiskCacheDir(Context context, String uniqueName) {
        boolean externalStorageAvailable = Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED);
        final String cachePath;
        if (externalStorageAvailable) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
            Log.i(TAG,cachePath);
        }
        return new File(cachePath + File.separator + uniqueName);
    }
    //将Url转化为键值
    private String hashKeyFormUrl(String url){
        String cacheKey;
        try{
            //MessageDigest can provide algorithm for us
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            cacheKey = byteToHexString(mDigest.digest());
        }catch (NoSuchAlgorithmException e){
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    private String byteToHexString(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<bytes.length; i++){
            String hex = Integer.toHexString(0xFF&bytes[i]);
            if(hex.length()==1){
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    //获得可用空间大小
    private long getUsableSpace(File path){
        //judge the version of the current SDK
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.GINGERBREAD){
            return path.getUsableSpace();
        }
        //statfs for getting the status of the disk
        final StatFs stats = new StatFs(path.getPath());
        return (long)stats.getBlockSize()*(long)stats.getAvailableBlocks();
    }

    //最终加载的结果
    private static class LoaderResult{
        public ImageView imageView;
        public String uri;
        public Bitmap bitmap;

        public LoaderResult(ImageView imageView,String uri,Bitmap bitmap){
            this.imageView = imageView;
            this.uri = uri;
            this.bitmap = bitmap;
        }
    }
}