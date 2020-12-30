package tech.jayamakmurdigital.spamdetector.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import tech.jayamakmurdigital.spamdetector.databinding.FragmentMessageCheckerBinding


class MessageCheckerFragment : Fragment() {

    lateinit var binding: FragmentMessageCheckerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMessageCheckerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.IDBTNCheckMessage.setOnClickListener {
            val message = binding.IDMessageInput.text.toString()
            val server = binding.IDSPAMSERVER.text.toString()
//            SPAMChecker(it.context, server).checkMessage(message).observe(viewLifecycleOwner) {
//                binding.IDResultScore.text = "$it%"
//            }
        }
    }
}
