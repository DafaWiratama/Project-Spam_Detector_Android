package tech.jayamakmurdigital.spamdetector.fragment.contact_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.isDigitsOnly
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import tech.jayamakmurdigital.spamdetector.databinding.LayoutContactItemBinding
import tech.jayamakmurdigital.spamdetector.model.Contact
import tech.jayamakmurdigital.spamdetector.utils.toString
import java.util.*


class ContactAdapter(private val list: Array<Contact>) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class ViewHolder(private val binding: LayoutContactItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Contact) {
            binding.IDContactName.text = data.name
            binding.IDContactLastMessageTime.text =
                if (data.getLastMessage.time.toString("dd") == Calendar.getInstance().timeInMillis.toString("dd"))
                    data.getLastMessage.time.toString("HH:mm")
                else data.getLastMessage.time.toString("dd MMM")
            binding.IDContactLastMessage.text = data.getLastMessage.text
            binding.IDContactUnReadCount.text = if (data.getUnreadMessages > 99) "99+" else data.getUnreadMessages.toString()
            if (data.getUnreadMessages == 0) binding.IDContactUnReadCount.visibility = View.GONE
            if (data.name.replace("+", "").isDigitsOnly()) binding.IDContactIcon.visibility = View.VISIBLE
            else binding.IDContactAlias.text = data.name.first().toString()

            binding.IDSPAMBadge.visibility = if (data.messages.filter { it.spamScore ?: 0 > 80 }.count() != 0) View.VISIBLE else View.GONE

            binding.root.setOnClickListener {
                binding.root.findNavController().navigate(ContactFragmentDirections.actionContactListFragmentToMessagerFragment(data))
            }
        }
    }
}