package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySignUpBinding


class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpSignUpBtn.setOnClickListener{
            signUp()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    // 사용자가 입력한값을 가져오는 함수
    private fun getUser() : User{
        val email = binding.signUpIdEt.text.toString() + "@" + binding.signUpDirectInputEt.text.toString()
        val pwd = binding.signUpPasswordEt.text.toString()

        return User(email,pwd)
    }


    private fun signUp() : Boolean {
        if(binding.signUpIdEt.text.toString().isEmpty() || binding.signUpDirectInputEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return false
        }

        if(binding.signUpPasswordEt.text.toString() != binding.signUpPasswordCheckEt.text.toString()) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return false
        }

        if(binding.signUpNameEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이름 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return false
        }

        // room에 저장
        val userDB = SongDataBase.getInstance(this)!!
        userDB.userDao().insert(getUser())

        val user = userDB.userDao().getUsers()
        Log.d("Signup", user.toString())
        return true
    }
}