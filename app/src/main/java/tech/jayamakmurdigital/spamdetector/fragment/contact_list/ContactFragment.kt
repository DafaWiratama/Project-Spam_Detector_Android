package tech.jayamakmurdigital.spamdetector.fragment.contact_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.jayamakmurdigital.spamdetector.database.Repository
import tech.jayamakmurdigital.spamdetector.databinding.FragmentListContactBinding
import tech.jayamakmurdigital.spamdetector.utils.PermissionManager
import tech.jayamakmurdigital.spamdetector.utils.SMSHelper
import tech.jayamakmurdigital.spamdetector.utils.SMSLoader


class ContactFragment : Fragment() {
    private lateinit var binding: FragmentListContactBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentListContactBinding.inflate(inflater).apply {
            IDMessages.layoutManager = LinearLayoutManager(context)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PermissionManager(this).getPermission()
        val loader = SMSLoader(requireContext())
        LoaderManager.getInstance(this).initLoader(0, null, loader)
        loader.messages.observe(viewLifecycleOwner) { messages ->
            var unread = 0
            messages.forEach { if (!it.read) unread++ }
            binding.IDUnreadCount.text = "$unread Unread Message"
        }
        loader.groupMessages.observe(viewLifecycleOwner) { contacts ->
        }
        CoroutineScope(Dispatchers.IO).launch {
            val contacts = SMSHelper().groupMessage(Repository.db.messageDao().messages)
            binding.IDMessages.adapter = ContactAdapter(contacts)
        }
    }
}
