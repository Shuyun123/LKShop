package net.anumbrella.lkshop.widget;

/**
 * author：Anumbrella
 * Date：16/6/25 下午1:41
 */
public interface ProgressListener {
    /**
     * @param progress 已经下载或上传字节数
     * @param total    总字节数
     * @param done     是否完成
     */
    void onProgress(long progress, long total, boolean done);
}