package com.example.plan_me.ui.setting

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.plan_me.databinding.ActivityAccountBinding
import com.example.plan_me.ui.CircleTransform
import com.example.plan_me.ui.dialog.DialogDeleteActivity
import com.example.plan_me.ui.dialog.DialogLogoutActivity
import com.example.plan_me.ui.login.InitProfileActivity
import com.squareup.picasso.Picasso

class AccountActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAccountBinding
    private var userName: String? = ""
    private var userImg: String? = ""
    private var social: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()

        binding.accountNameTv.text = userName
        if (userImg != "https://k.kakaocdn.net/dn/1G9kp/btsAot8liOn/8CWudi3uy07rvFNUkk3ER0/img_640x640.jpg") {
            Picasso.get().load(userImg).transform(CircleTransform())
                .into(binding.accountImageIv)
        }
        binding.accountSocialTypeTv.text = social

        binding.accountBackBtn.setOnClickListener {
            finish()
        }

        binding.accountCameraLo.setOnClickListener {
            // 권한이 있는지 확인
            if (checkPermission()) {
                // 이미지 선택 화면으로 이동
                openImagePicker()
            } else {
                // 권한 요청
                requestPermission()
            }
        }

        binding.accountNicknameBtn.setOnClickListener {
            switchActivity(ChangeNicknameActivity())
        }

        binding.accountSocialBtn.setOnClickListener {
            switchActivity(ChangeTypeActivity())
        }

        binding.accountLogoutTv.setOnClickListener {
            showDialog(DialogLogoutActivity(this@AccountActivity))
        }

        binding.accountDeleteTv.setOnClickListener {
            showDialog(DialogDeleteActivity(this@AccountActivity))
        }
    }

    private fun showDialog(dialog: Dialog) {
        dialog.show()
    }

    private fun switchActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun getData() {
        // 데이터 읽어오기
        val sharedPreferences: SharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE)
        userName = sharedPreferences.getString("userName", userName)
        userImg = sharedPreferences.getString("userImg", userImg)
        social = sharedPreferences.getString("social", social)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkPermission(): Boolean {
        Log.d("checkPermission", "check")
        return (ContextCompat.checkSelfPermission(
            this, Manifest.permission.READ_MEDIA_IMAGES
        ) == PackageManager.PERMISSION_GRANTED)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestPermission() {
        Log.d("requestPermission", "request")
        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES)) {
            // 사용자에게 왜 권한이 필요한지 설명을 보여줄 수 있는 로직 추가
            // Android 6.0 (API 23) 이상에서는 권한을 동적으로 요청할 때 왜 필요한 지에 대한 설명 필요
            Log.d("permission", "사진 권한 요청")
        }
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
            InitProfileActivity.REQUEST_PERMISSION_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == InitProfileActivity.REQUEST_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker()
            } else {
                // 권한이 거부된 경우
                Toast.makeText(this@AccountActivity, "사진 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, InitProfileActivity.REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == InitProfileActivity.REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
            // 이미지를 선택한 경우, 이미지뷰에 설정
            val selectedImageUri = data?.data
            binding.accountImageIv.setImageURI(selectedImageUri)
        }
    }

    companion object {
        private val REQUEST_PERMISSION_CODE = 423
        private val REQUEST_IMAGE_PICK = 826
    }
}