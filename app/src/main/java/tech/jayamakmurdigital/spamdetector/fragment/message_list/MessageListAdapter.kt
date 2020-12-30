package tech.jayamakmurdigital.spamdetector.fragment.message_list

import tech.jayamakmurdigital.spamdetector.model.SMS
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import tech.jayamakmurdigital.spamdetector.databinding.LayoutMessageItemBinding
import tech.jayamakmurdigital.spamdetector.utils.toString


class MessageListAdapter(private val list: Array<SMS>, val lifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<MessageListAdapter.ViewHolder>() {
    override fun getItemCount() = list.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutMessageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], lifecycleOwner)
        try {
            if (list[position - 1].time.toString("dMMYYYY") == list[position].time.toString("dMMYYYY")) {
                holder.binding.IDMessageDate.visibility = View.GONE
                holder.binding.IDContactProfile.visibility = View.INVISIBLE
            }
        } catch (e: IndexOutOfBoundsException) {
        }
    }

    class ViewHolder(val binding: LayoutMessageItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: SMS, lifecycleOwner: LifecycleOwner) {
            binding.IDMessage.text = data.text
            if (data.text != "") data.getSpamScore(binding.root.context).observe(lifecycleOwner) {
                if (it > 80) {
                    binding.IDMessage.alpha = 0.05f
                    binding.IDSPAMALERT.visibility = View.VISIBLE
                    binding.IDMessageContainer.setOnClickListener {
                        binding.IDSPAMALERT.visibility = View.INVISIBLE
                        binding.IDMessage.alpha = 0.8f
                        binding.IDSPAMBadge.visibility = View.VISIBLE
                    }
                }
                Log.d("Eirene", "bind: $it")
            }
            binding.IDMessageTime.text = data.time.toString("HH:mm")
            binding.IDMessageDate.text = data.time.toString("EEEE, MMMM d, YYYY")
            if (data.name.replace("+", "").isDigitsOnly())
                binding.IDMessageIcon.visibility = View.VISIBLE
            else binding.IDContactAlias.text = data.name.first().toString()
        }

        fun onClick(view: View, data: SMS) {
        }
    }
}