package com.qyc.lock;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

	private DevicePolicyManager mDevicePolicyManager;
	private ComponentName mComponentName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); // 获取设备管理器
		mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		mComponentName = new ComponentName(this, LockReceiver.class); // 判断该组件是否有系统管理员的权限
		if (mDevicePolicyManager.isAdminActive(mComponentName)) {
			mDevicePolicyManager.lockNow(); // 锁屏
		} else {
			activeManager(); // 激活权限
		}
		// 结束进程
		int pid = android.os.Process.myPid();
		android.os.Process.killProcess(pid);
	}

	/** 激活设备管理器获取权限 */
	protected void activeManager() {
		Toast.makeText(this, "注意：\n激活后可能无法卸载该应用。\n如果要卸载，请先到设置-安全-设备管理器里取消激活.", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Lock Device");
		startActivity(intent);
	}
}
