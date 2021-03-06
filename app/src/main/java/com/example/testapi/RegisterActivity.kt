package com.example.testapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class RegisterActivity : AppCompatActivity() {


    private var userid: String? = null

    private var usersViewModel: UsersViewModel = UsersViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        if (intent.getStringExtra("userid") != "") {
            userid = intent.getStringExtra("userid")
            nameTextField.setText(intent.getStringExtra("name").toString())
            textField.setText(intent.getStringExtra("text").toString())


        }
        button.setOnClickListener {

            if (nameTextField.text.isEmpty()) {
                showAlert("名前が入力されていません", false)
                return@setOnClickListener
            }
            if (textField.text.isEmpty()) {
                showAlert("テキストが入力されていません", false)
                return@setOnClickListener
            }

            if (intent.getStringExtra("userid") != "") {
                usersViewModel.updateText(userid.toString(), nameTextField.text.toString(), textField.text.toString()
                ) { result ->
                    if (result) {
                        Toast.makeText(applicationContext, "更新しました", Toast.LENGTH_LONG).show()
                        finish()

                    } else {
                        Toast.makeText(applicationContext, "更新に失敗しました", Toast.LENGTH_LONG).show()

                    }
                }

            } else {
                usersViewModel.post(nameTextField.text.toString(), textField.text.toString()) { result ->
                    if (result) {
                        Toast.makeText(applicationContext,"登録しました", Toast.LENGTH_LONG).show()
                        finish()

                    } else {
                        Toast.makeText(applicationContext,"登録に失敗しました", Toast.LENGTH_LONG).show()

                    }
                }

            }
        }
    }

    /// yesButtonを押した時に画面を閉じるアラート
    private fun showAlert(message: String, isFinish: Boolean) {
        alert(message) {
            yesButton {
                if (isFinish) {
                    finish()
                }
            }
        }.show()
    }
}
