package tech.jayamakmurdigital.spamdetector.fragment.message_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.IDContactName.text = inboxContact.name
        binding.IDMessages.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = MessageListAdapter(inboxContact.messages.toTypedArray(),viewLifecycleOwner)
            scrollToPosition(inboxContact.messages.size - 1)
        }
    }
}
