package com.jykj.live.beauty;

public interface Downloadlistener {
    void onDownloadFail(String errorMsg);

    void onDownloadProgress(final int progress);

    void onDownloadSuccess(String filePath);
}
