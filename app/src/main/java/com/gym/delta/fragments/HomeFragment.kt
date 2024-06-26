package com.gym.delta.fragments

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.gym.delta.R

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        All of below is just making buttons navigate to other activities
//
//        val button = view.findViewById<Button>(R.id.navigate_destination_button)
//        button?.setOnClickListener {
//            findNavController().navigate(R.id.workouts_dest, null)
//        }
//
//        //TODO STEP 6 - Set NavOptions
//        val options = navOptions {
//            anim {
//                enter = R.anim.slide_in_right
//                exit = R.anim.slide_out_left
//                popEnter = R.anim.slide_in_left
//                popExit = R.anim.slide_out_right
//            }
//        }
//        view.findViewById<Button>(R.id.navigate_destination_button)?.setOnClickListener {
//            findNavController().navigate(R.id.workouts_dest, null, options)
//        }
//        //TODO END STEP 6
//
//        //TODO STEP 7.2 - Update the OnClickListener to navigate using an action
//        view.findViewById<Button>(R.id.navigate_action_button)?.setOnClickListener(
//                Navigation.createNavigateOnClickListener(R.id.next_action, null)
//        )
//        //TODO END STEP 7.2

        
    }
}
