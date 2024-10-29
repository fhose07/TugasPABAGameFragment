package paba.c14220151.latihangamesfragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private var currentScore = 50


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // setting navigation bar
        val navBar = findViewById<BottomNavigationView>(R.id.NavigationView)
        navBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                //diarahkan ke game
                R.id.action_game -> {
                    loadFragment(GameFragment())
                    true
                }
                //diarahkan ke bagian result
                R.id.action_result -> {
                    val fragment = ResultFragment()
                    val bundle = Bundle()
                    bundle.putInt("score", currentScore)
                    fragment.arguments = bundle
                    loadFragment(fragment)
                    true
                }
                //diarahkan ke bagian pengaturan
                R.id.action_settings -> {
                    loadFragment(SettingFragment())
                    true
                }
                else -> false
            }
        }
        loadFragment(GameFragment())
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    fun updateScore(score: Int) {
        currentScore = score
    }
}