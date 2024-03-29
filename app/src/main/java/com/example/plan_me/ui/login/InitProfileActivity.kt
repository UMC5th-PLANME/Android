package com.example.plan_me.ui.login

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.plan_me.ui.main.MainActivity
import com.example.plan_me.R
import com.example.plan_me.data.local.entity.EditProfile
import com.example.plan_me.data.remote.dto.auth.ChangeMemberRes
import com.example.plan_me.data.remote.dto.auth.ProfileImageRes
import com.example.plan_me.data.remote.service.auth.ImageService
import com.example.plan_me.data.remote.service.auth.MemberService
import com.example.plan_me.data.remote.view.auth.ChangeProfileView
import com.example.plan_me.data.remote.view.auth.ProfileImageView
import com.example.plan_me.databinding.ActivityInitProfileBinding
import com.example.plan_me.ui.CircleTransform
import com.example.plan_me.ui.dialog.CustomToast
import com.squareup.picasso.Picasso
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class InitProfileActivity : AppCompatActivity(), ProfileImageView, ChangeProfileView {
    private lateinit var binding: ActivityInitProfileBinding
    private var userName: String? = ""
    private var userImg: String? = ""
    private var userEmail: String? = ""
    private var userType: String? = ""
    private var accessToken: String? = ""
    private lateinit var imageResult: ActivityResultLauncher<Intent>
    private var newImgUrl: String? = ""
    private var customToast = CustomToast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        overridePendingTransition(R.anim.screen_start, R.anim.screen_none)

        getData()
        getRemoteData()

        imageResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){ result ->
            Log.d("result", result.toString())
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUrl = result.data?.data ?: return@registerForActivityResult

                // 이미지를 선택한 경우, 이미지뷰에 설정
                binding.initProfileImagefileIv.setImageURI(imageUrl)
                Picasso.get().load(imageUrl).transform(CircleTransform()).into(binding.initProfileImagefileIv)

                val file = File(absolutelyPath(imageUrl, this@InitProfileActivity))
                val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
                val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

                binding.initProfileImagefileIv.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
                    // 이미지 변경 감지 시 처리할 로직을 여기에 작성
                    // 예를 들어, 이미지 변경 후에 어떤 작업을 수행할 수 있습니다.
                    // 변경된 이미지 정보를 사용하여 추가적인 작업을 수행할 수 있습니다.
                    Log.d("test-image", file.name)
                    setImageService(body)
                }

                Log.d("imageUrl", imageUrl.toString())
                binding.initProfileImagefileIv.setImageURI(imageUrl)
            }
        }

        binding.initProfileNameTv.setText(userName)
        if (userImg != "https://k.kakaocdn.net/dn/1G9kp/btsAot8liOn/8CWudi3uy07rvFNUkk3ER0/img_640x640.jpg" && userImg != "null") {
            Picasso.get().load(userImg).transform(CircleTransform())
                .into(binding.initProfileImagefileIv)
        }

        binding.initProfileNameTv.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                // 입력 전에 호출되는 메서드
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트가 변경될 때 호출되는 메서드
                userName = charSequence.toString()
                // 여기에서 변경된 텍스트에 대한 처리를 수행할 수 있습니다.
            }

            override fun afterTextChanged(editable: Editable?) {
                // 입력이 완료된 후에 호출되는 메서드
            }
        })

        binding.initProfileCameraLo.setOnClickListener {
            // 권한이 있는지 확인
            if (checkPermission()) {
                // 이미지 선택 화면으로 이동
                openImagePicker()
            } else {
                // 권한 요청
                requestPermission()
            }
        }

        binding.initProfileCompletBtn.setOnClickListener {
            setEditProfileService()
        }
    }

    // 절대경로 변환
    private fun absolutelyPath(path: Uri?, context : Context): String {
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        var result = c?.getString(index!!)

        return result!!
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
            REQUEST_PERMISSION_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker()
            } else {
                // 권한이 거부된 경우
                customToast.createToast(this@InitProfileActivity,"사진 권한이 거부되었습니다.", 300, false)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onBackPressed() {}

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        imageResult.launch(intent)
    }

    private fun setImageService(image: MultipartBody.Part) {
        val setImageService = ImageService()
        setImageService.setImageView(this@InitProfileActivity)
        setImageService.setProfileImg("Bearer " + accessToken, image)
    }

    private fun getData() {
        // 데이터 읽어오기
        val sharedPreferences: SharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE)
        userName = sharedPreferences.getString("userName", userName)
        userImg = sharedPreferences.getString("userImg", userImg)
        userEmail = sharedPreferences.getString("email", userEmail)
        userType = sharedPreferences.getString("social", userType)
    }

    private fun getRemoteData() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("getRes", MODE_PRIVATE)
        accessToken = sharedPreferences.getString("getAccessToken", accessToken)
    }

    private fun goMainActivity() {
        val intent = Intent(this@InitProfileActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
    }

    private fun setEditProfileService() {
        val setEditProfileService = MemberService()
        setEditProfileService.setChangeProfileView(this@InitProfileActivity)

        if (newImgUrl == "") { // 이미지 변경 안 했을 경우
            Log.d("revise-not-image", "이미지 변경 X")
            val member = EditProfile(userName!!, userImg!!)
            setEditProfileService.setChangeProfile("Bearer " + accessToken!!, member)
        } else { // 이미지 변경 했을 경우
            Log.d("revise-image", "이미지 변경 O")
            val member = EditProfile(userName!!, newImgUrl!!)
            setEditProfileService.setChangeProfile("Bearer " + accessToken!!, member)
        }
    }

    companion object {
        val REQUEST_PERMISSION_CODE = 423
    }

    override fun onSetImgSuccess(response: ProfileImageRes) {
        Log.d("이미지 등록", response.result.toString())
        newImgUrl = response.result.imageUrl
    }

    override fun onSetImgFailure(isSuccess: Boolean, code: String, message: String) {
        Log.d("이미지 등록 실패", message)
    }

    override fun onSetChangeProfileSuccess(response: ChangeMemberRes) {
        Log.d("프로필 변경", response.result.toString())
        goMainActivity()
        overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
    }

    override fun onSetChangeProfileFailure(isSuccess: Boolean, code: String, message: String) {
        Log.d("프로필 변경 실패", message)
    }
}