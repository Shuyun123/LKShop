package net.anumbrella.lkshop.widget;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import net.anumbrella.lkshop.model.bean.ProgressBean;

/**
 * author：Anumbrella
 * Date：16/6/25 下午1:38
 */
public abstract class ProgressHandler {
    protected abstract void sendMessage(ProgressBean progressBean);

    protected abstract void handleMessage(Message message);

    protected abstract void onProgress(long progress, long total, boolean done);

    protected static class ResponseHandler extends Handler {

        private ProgressHandler mProgressHandler;
        public ResponseHandler(ProgressHandler mProgressHandler, Looper looper) {
            super(looper);
            this.mProgressHandler = mProgressHandler;
        }

        @Override
        public void handleMessage(Message msg) {
            mProgressHandler.handleMessage(msg);
        }
    }

}
