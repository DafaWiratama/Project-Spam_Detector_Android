package tech.jayamakmurdigital.spamdetector.fragment.message_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.jayamakmurdigital.spamdetector.database.Repository
import tech.jayamakmurdigital.spamdetector.databinding.FragmentListMessageBinding
import tech.jayamakmurdigital.spamdetector.model.Contact


class MassageListFragment : Fragment() {

    private lateinit var inboxContact: Contact
    private lateinit var binding: FragmentListMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inboxContact = requireArguments().getParcelable("contact")!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentListMessageBinding.inflate(inflater)
        binding.IDBack.setOnClickListener { requireActivity().onBackPressed() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inboxContact.messages.forEach {
            CoroutineScope(Dispatchers.IO).launch { Repository.db.messageDao().update(it.apply { read = true }) }
        }

        binding.IDContactName.text = inboxContact.name
        binding.IDMessages.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = MessageListAdapter(inboxContact.messages.toTypedArray())
            scrollToPosition(inboxContact.messages.size - 1)
        }
    }
}
