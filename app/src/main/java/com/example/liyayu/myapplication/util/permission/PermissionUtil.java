package com.example.liyayu.myapplication.util.permission;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;


import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jinliangshan on 17/4/26.
 *
 * TZActivity 和 TZFragment 中, 关于 权限 的统一实现.
 * 封装的 EasyPermissions
 */
public class PermissionUtil {

    private static final String TAG = "PermissionUtil";

    static Deque<Callback> sCallbackQueue = new LinkedList<>();

    /**
     * 见 {@link PermissionManager#doTaskWithPermissions(String, String[], Callback)}
     */
    public static void doTaskWithPermissions(Activity activity, String rationale, String[] perms, Callback callback) {
        if (hasPermissions(activity, perms)) {
            // Have permission, do the thing!
            callback.onAfterAllPermissionGranted(callback.getRequestCode(), Arrays.asList(perms));

        } else {
            // Ask for permission
            sCallbackQueue.offer(callback);
            requestPermissions(activity, rationale, callback.getRequestCode(),callback, perms);
        }
    }

    /**
     * 有权限
     *
     * @param context
     * @param perms 请求权限列表
     * @return
     */
    public static boolean hasPermissions(Context context, @NonNull String... perms) {
        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 请求权限
     *
     * @param host 所在的 activity
     * @param rationale 请求权限原因(用于告知用户)
     * @param requestCode 请求码, 用在 onActivityResult()
     * @param perms 请求权限列表
     */
    public static void requestPermissions(@NonNull Activity host, @NonNull String rationale, int requestCode, Callback callback, @NonNull String... perms) {
        EasyPermissions.requestPermissions(host, rationale, requestCode, callback, perms);
    }

    /**
     * 权限授予结果回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Callback callback = sCallbackQueue.poll();
        if(callback != null)
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, callback);
    }

    /**
     * 权限管理
     */
    public interface PermissionManager{
        /**
         * 执行任务 task 前, 先检查需要的权限
         * 1. 有权限 -> 直接执行 task
         * 2. 无权限 -> 先请求权限
         *      2.a 用户拒绝 -> 回调 {@link EasyPermissions.PermissionCallbacks#onPermissionsDenied(int, List)}
         *      2.b 用户允许 -> 回调 {@link EasyPermissions.PermissionCallbacks#onPermissionsGranted(int, List)},
         *          并且回调被注解的函数{@link EasyPermissions.PermissionCallbacks#onAfterAllPermissionGranted(int, List)}
         *          (以 requestCode 区分).
         *
         * 例:
         * <pre>
         *  doTaskWithPermissions("需要相机权限", new String[]{Manifest.permission.CAMERA}, new PermissionUtil.Callback(requestCode) {
         *      @Override
         *      public void onAfterAllPermissionGranted(int requestCode, List<String> perms) {
         *          showToast("权限已授予");
         *      }
         *
         *      @Override
         *      public void onPermissionsDenied(int requestCode, List<String> perms) {
         *          showToast("权限被拒绝");
         *      }
         *  });
         * </pre>
         *
         * @param rationale 请求权限原因(用于告知用户)
         * @param perms 请求权限列表
         * @param callback 回调的 task
         */
        void doTaskWithPermissions(String rationale, String[] perms, Callback callback);
    }

    /**
     * 权限授予情况的回调, 使用时注意:
     *
     *   若有 onActivityResult() 回调, 则需要指定 {@link Callback#mRequestCode}
     */
    public static abstract class Callback implements EasyPermissions.PermissionCallbacks{
        /**
         * onActivityResult() 里边使用, 以区分请求.
         * 其他情况可省略该字段
         */
        private int mRequestCode = -0xffffffff;

        public Callback() {}

        public Callback(int requestCode) {
            mRequestCode = requestCode;
        }

        /**
         * 根据 {@link BaseFragmentActivityGingerbread#checkForValidRequestCode(int requestCode)}
         * requestCode 必须是低16位
         *
         * @return
         */
        public int getRequestCode(){
            if(mRequestCode == -0xffffffff)
                return mRequestCode = (this.hashCode() & 0x0000ffff);
            return mRequestCode;
        }

        /**
         * 所有权限都被授予, 共回调一次
         */
        @Override
        public abstract void onAfterAllPermissionGranted(int requestCode, List<String> perms);

        /**
         * 每一个权限被拒绝时, 都会触发一次
         */
        @Override
        public abstract void onPermissionsDenied(int requestCode, List<String> perms);

        /**
         * 以下两个接口是 PermissionCallbacks 中冗余的, 不宜使用
         */
        /**
         * 当有多个权限时, 不宜使用这个接口, 而是用 {@link #onAfterAllPermissionGranted(int, List)}作为代替
         *
         * @param requestCode
         * @param perms
         */
        @Override
        final public void onPermissionsGranted(int requestCode, List<String> perms) {}

        @Override
        final public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {}
    }
}
