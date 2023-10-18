package mx.gldm.userappl.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import mx.gldm.userappl.R
import mx.gldm.userappl.databinding.ActivityMainBinding
import android.graphics.Bitmap
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import android.widget.ImageView
import mx.gldm.userappl.ui.home.HomeFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


//        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
//        val fragment = HomeFragment()
//        transaction.replace(R.id.nav_host_fragment_activity_main, fragment, "homeFragment")
//        transaction.commit()
//
//// Encuentra el fragmento que contiene el ImageView
//        val fragmentManager: FragmentManager = supportFragmentManager
//        val fragment = fragmentManager.findFragmentByTag("homeFragment")
//        if (fragment is HomeFragment) {
//            val imageViewQR = fragment.view?.findViewById<ImageView>(R.id.imgQR)
//            if (imageViewQR != null) {
//                // Obtén el código QR de los extras del Intent
//                val qrCodeBitmap = intent.getParcelableExtra("QRCode") as? Bitmap
//                if (qrCodeBitmap != null) {
//                    imageViewQR.setImageBitmap(qrCodeBitmap)
                }
            }


//        }
//
//    }
//}
