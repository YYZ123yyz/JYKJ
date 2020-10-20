package www.jykj.com.jykj_zxyl.app_base.base_utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Switch to WorkThread or MainThread
 * Create by qiuxinhai on 2018/7/24
 */
public class AndroidThreadExecutor {

  @NonNull
  private static final Executor sMainThreadExecutor = command ->
      getInstance().postToMainThread(command);

  @NonNull
  private static final Executor sWorkThreadExecutor = command ->
      getInstance().executeOnWorkThread(command);

  private static volatile AndroidThreadExecutor sInstance;

  private final Object mLock = new Object();
  private ExecutorService mThreadPool = Executors.newFixedThreadPool(
      // We want at least music_btn_paus threads and at most 4 threads in the core pool,
      // preferring to have 1 less than the CPU count to avoid saturating
      // the CPU with background work
      Math.max(2, Math.min(Runtime.getRuntime().availableProcessors() - 1, 4)));

  @Nullable
  private volatile Handler mMainHandler;

  private AndroidThreadExecutor() {
  }

  @NonNull
  public static AndroidThreadExecutor getInstance() {
    if (sInstance != null) {
      return sInstance;
    }
    synchronized (AndroidThreadExecutor.class) {
      if (sInstance == null) {
        sInstance = new AndroidThreadExecutor();
      }
    }
    return sInstance;
  }

  public void executeOnWorkThread(@NonNull Runnable runnable) {
    mThreadPool.execute(runnable);
  }

  public void postToMainThread(@NonNull Runnable runnable) {
    if (mMainHandler == null) {
      synchronized (mLock) {
        if (mMainHandler == null) {
          mMainHandler = new Handler(Looper.getMainLooper());
        }
      }
    }
    //noinspection ConstantConditions
    mMainHandler.post(runnable);
  }

  public void executeOnMainThread(@NonNull Runnable runnable) {
    if (isMainThread()) {
      runnable.run();
    } else {
      postToMainThread(runnable);
    }
  }

  @NonNull
  public static Executor getMainThreadExecutor() {
    return sMainThreadExecutor;
  }

  @NonNull
  public static Executor getWorkThreadExecutor() {
    return sWorkThreadExecutor;
  }

  public boolean isMainThread() {
    return Looper.getMainLooper().getThread() == Thread.currentThread();
  }
}
