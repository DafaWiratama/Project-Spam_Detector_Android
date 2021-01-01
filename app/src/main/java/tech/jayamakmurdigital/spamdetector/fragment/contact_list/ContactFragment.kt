package tech.jayamakmurdigital.spamdetector.fragment.contact_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.jayamakmurdigital.spamdetector.R
import tech.jayamakmurdigital.spamdetector.database.Repository
import tech.jayamakmurdigital.spamdetector.databinding.FragmentListContactBinding
import tech.jayamakmurdigital.spamdetector.utils.PermissionManager


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
        CoroutineScope(Dispatchers.IO).launch {
            val contacts = Repository.db.messageDao().contacts
            val unread = Repository.db.messageDao().unread
            launch(Dispatchers.Main) {
                binding.IDMessages.adapter = ContactAdapter(contacts)
                binding.IDUnreadCount.text = "$unread Unread Message"
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val popup = PopupMenu(requireContext(), binding.IDDeveloper)
        popup.menu.add("Reset Unread")
        popup.menu.add("Menu2 Label")
        popup.menuInflater.inflate(R.menu.default_menu, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            when (item) {
                popup.menu[0] -> {

                }
                popup.menu[1] -> {
                }
            }
            true
        }
        binding.IDDeveloper.setOnClickListener { popup.show() }
    }
}
