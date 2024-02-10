package com.example.plan_me.ui.setting

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.plan_me.R
import com.example.plan_me.data.local.entity.EditProfile
import com.example.plan_me.data.remote.dto.auth.ChangeMemberRes
import com.example.plan_me.data.remote.dto.auth.MemberRes
import com.example.plan_me.data.remote.dto.auth.ProfileImageRes
import com.example.plan_me.data.remote.service.auth.ImageService
import com.example.plan_me.data.remote.service.auth.MemberService
import com.example.plan_me.data.remote.view.auth.ChangeProfileView
import com.example.plan_me.data.remote.view.auth.LookUpMemberView
import com.example.plan_me.data.remote.view.auth.ProfileImageView
import com.example.plan_me.databinding.ActivityAccountBinding
import com.example.plan_me.ui.CircleTransform
import com.example.plan_me.ui.dialog.DialogDeleteActivity
import com.example.plan_me.ui.dialog.DialogLogoutActivity
import com.example.plan_me.ui.login.InitProfileActivity
import com.squareup.picasso.Picasso
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class AccountActivity: AppCompatActivity(), ChangeProfileView, ProfileImageView, LookUpMemberView {
    private lateinit var binding: ActivityAccountBinding
    private var userName: String? = ""
    private var userImg: String? = DEFAULT_IMG
    private var social: String? = ""
    private var accessToken: String? = ""
    private lateinit var imageResult: ActivityResultLauncher<Intent>
    private var newImgUrl: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        overridePendingTransition(R.anim.screen_start, R.anim.screen_none)

        getData2()
        setLookUpService()

        updateUI()

        imageResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){ result ->
            Log.d("result", result.toString())
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUrl = result.data?.data ?: return@registerForActivityResult

                // 이미지를 선택한 경우, 이미지뷰에 설정
                binding.accountImageIv.setImageURI(imageUrl)
                Picasso.get().load(imageUrl).transform(CircleTransform()).into(binding.accountImageIv)

                val file = File(absolutelyPath(imageUrl, this@AccountActivity))
                val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
                val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

                binding.accountImageIv.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
                    // 이미지 변경 감지 시 처리할 로직을 여기에 작성
                    // 예를 들어, 이미지 변경 후에 어떤 작업을 수행할 수 있습니다.
                    // 변경된 이미지 정보를 사용하여 추가적인 작업을 수행할 수 있습니다.
                    Log.d("test-image", file.name)
                    setImageService(body)
                }

                Log.d("imageUrl", imageUrl.toString())
                binding.accountImageIv.setImageURI(imageUrl)
            }
        }

        binding.accountBackBtn.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
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

        binding.accountNicknameLo.setOnClickListener {
            switchActivity(ChangeNicknameActivity())
        }

        binding.accountSocialLo.setOnClickListener {
            switchActivity(ChangeTypeActivity())
        }

        binding.accountLogoutTv.setOnClickListener {
            showDialog(DialogLogoutActivity(this@AccountActivity, social))
        }

        binding.accountDeleteTv.setOnClickListener {
            showDialog(DialogDeleteActivity(this@AccountActivity, accessToken!!))
        }
    }

    private fun showDialog(dialog: Dialog) {
        dialog.show()
    }

    private fun switchActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
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
            InitProfileActivity.REQUEST_PERMISSION_CODE
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
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
        imageResult.launch(intent)
    }

    private fun getData2() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("getRes", MODE_PRIVATE)
        accessToken = sharedPreferences.getString("getAccessToken", accessToken)
    }

    private fun setImageService(image: MultipartBody.Part) {
        val setImageService = ImageService()
        setImageService.setImageView(this@AccountActivity)
        setImageService.setProfileImg("Bearer " + accessToken, image)
    }

    private fun setEditProfileService() {
        val setEditProfileService = MemberService()
        setEditProfileService.setChangeProfileView(this@AccountActivity)

        Log.d("revise-image", "이미지 변경 O")
        val member = EditProfile(userName!!, newImgUrl!!)
        setEditProfileService.setChangeProfile("Bearer " + accessToken!!, member)
    }

    private fun setLookUpService() {
        val setLookUpService = MemberService()
        setLookUpService.setLookUpMemberView(this@AccountActivity)
        setLookUpService.getLookUpMember("Bearer " + accessToken)
    }

    private fun updateUI() {
        binding.accountNameTv.text = userName
        if (userImg != DEFAULT_IMG && userImg != "null") {
            if (newImgUrl == "") {
                Picasso.get().load(userImg).transform(CircleTransform())
                    .into(binding.accountImageIv)
            } else {
                Picasso.get().load(newImgUrl).transform(CircleTransform())
                    .into(binding.accountImageIv)
            }
        }
        binding.accountSocialTypeTv.text = social
    }

    override fun onSetChangeProfileSuccess(response: ChangeMemberRes) {
        Log.d("이미지 변경", response.result.toString())
    }

    override fun onSetChangeProfileFailure(isSuccess: Boolean, code: String, message: String) {
        Log.d("이미지 변경 실패", message)
    }

    override fun onSetImgSuccess(response: ProfileImageRes) {
        Log.d("이미지 등록", response.result.toString())
        newImgUrl = response.result.imageUrl
        setEditProfileService()
    }

    override fun onSetImgFailure(isSuccess: Boolean, code: String, message: String) {
        Log.d("이미지 등록 실패", message)
    }

    override fun onGetMemberSuccess(response: MemberRes) {
        Log.d("회원 조회", response.result.toString())
        userName = response.result.nickname
        userImg = response.result.profile_image
        social = response.result.login_type

        // UI 업데이트
        updateUI()
    }

    override fun onGetMemberFailure(isSuccess: Boolean, code: String, message: String) {
        Log.d("회원 조회 실패", message)
    }

    companion object {
        val DEFAULT_IMG = "https://k.kakaocdn.net/dn/1G9kp/btsAot8liOn/8CWudi3uy07rvFNUkk3ER0/img_640x640.jpg"
    }
}