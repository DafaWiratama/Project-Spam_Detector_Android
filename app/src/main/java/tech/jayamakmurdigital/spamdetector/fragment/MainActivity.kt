package tech.jayamakmurdigital.spamdetector.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tech.jayamakmurdigital.spamdetector.database.Repository
import tech.jayamakmurdigital.spamdetector.databinding.ActivityMainBinding
import tech.jayamakmurdigital.spamdetector.utils.SMSLoader
import tech.jayamakmurdigital.spamdetector.utils.SpamDetector


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Repository.init(baseContext)

        CoroutineScope(Dispatchers.IO).launch {
            syncDatabase()
            scanForSpam()
        }
    }

    private suspend fun syncDatabase() = withContext(Dispatchers.Main) {
        val loader = SMSLoader(baseContext)
        LoaderManager.getInstance(this@MainActivity).initLoader(0, null, loader)
        loader.messages.observe(this@MainActivity) {
            CoroutineScope(Dispatchers.IO).launch {
                Repository.db.messageDao().insert(*it.toTypedArray())
            }
        }
    }

    private suspend fun scanForSpam() = withContext(Dispatchers.IO) {
        val ml = SpamDetector(baseContext)
        Repository.db.messageDao().messages.filter { it.spamScore == null }.forEach {
            it.spamScore = ml.predict(it.text)
            Repository.db.messageDao().update(it)
        }
    }
}


