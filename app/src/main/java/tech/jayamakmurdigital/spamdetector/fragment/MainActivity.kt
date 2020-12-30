package tech.jayamakmurdigital.spamdetector.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.jayamakmurdigital.spamdetector.database.Repository
import tech.jayamakmurdigital.spamdetector.databinding.ActivityMainBinding
import tech.jayamakmurdigital.spamdetector.utils.SMSLoader

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)

        initDatabase()
        syncDatabase()

    }

    private fun initDatabase(){
        Repository.init(baseContext)
    }

    private fun syncDatabase(){
        val loader = SMSLoader(baseContext)
        LoaderManager.getInstance(this).initLoader(0, null, loader)
        loader.messages.observe(this) {
            CoroutineScope(Dispatchers.IO).launch {
                Repository.db.messageDao().insert(*it.toTypedArray())
            }
        }
    }
}