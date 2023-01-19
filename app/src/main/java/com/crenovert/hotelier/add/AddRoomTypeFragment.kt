package com.crenovert.hotelier.add


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.crenovert.hotelier.R
import com.crenovert.hotelier.databinding.FragmentAddRoomTypeBinding
import com.crenovert.hotelier.data.Bed_Types_dt
import com.crenovert.hotelier.data.Room_Types_dt
import com.google.firebase.database.*


class AddRoomTypeFragment : Fragment(), CompoundButton.OnCheckedChangeListener {
    private var _binding: FragmentAddRoomTypeBinding? = null
    private val binding get() = _binding!!

    private var radioArray = arrayListOf<String>()
    private var checkBoxArray = arrayListOf<String>()

    private lateinit var mReferenceBT: DatabaseReference
    private lateinit var mReferenceRT: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddRoomTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getFireBaseData()

        binding.layoutAddBedType.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            val dialogLayout = layoutInflater.inflate(R.layout.fragment_add_bed_type, null)

            dialogLayout.findViewById<Button>(R.id.btnBT_cancle).visibility = View.VISIBLE
            builder.setView(dialogLayout)
            dialogLayout.setPadding(30, 30, 30, 30)

            var dataBT = Room_Types_dt()
            var alertDialog = builder.show()


            dialogLayout.findViewById<Button>(R.id.btnBT_cancle)
                .setOnClickListener { alertDialog.dismiss() }
            dialogLayout.findViewById<Button>(R.id.button_save).setOnClickListener {
                dataBT.checkBox = false
                dataBT.id =
                    Integer.parseInt(dialogLayout.findViewById<TextView>(R.id.edittext_bed_type_id).text.toString())
                dataBT.name =
                    dialogLayout.findViewById<TextView>(R.id.edittext_name).text.toString()
                mReferenceBT.child("${dataBT.id}").setValue(dataBT)
                alertDialog.dismiss()
            }
        }


        binding.cbFeatureAircon.setOnCheckedChangeListener(this)
        binding.cbFeatureBreakfast.setOnCheckedChangeListener(this)
        binding.cbFeatureNews.setOnCheckedChangeListener(this)
        binding.cbFeaturePowerbackup.setOnCheckedChangeListener(this)
        binding.cbFeatureSwimmingpool.setOnCheckedChangeListener(this)
        binding.cbFeatureWifi.setOnCheckedChangeListener(this)


        mReferenceRT = FirebaseDatabase.getInstance().getReference("room_types")
        var dataRT = Room_Types_dt()
        binding.radioGroupBT.setOnCheckedChangeListener { group, checkedId ->
            dataRT.bed_type = radioArray[binding.radioGroupBT.checkedRadioButtonId]
            //  Toast.makeText(activity?.applicationContext, "checkedId: " + dataRT.bed_type, Toast.LENGTH_SHORT).show()
        }


        binding.btnRTSave.setOnClickListener {
            if (binding.dataRTId.text.toString().isBlank() || binding.dataRTName.text.toString()
                    .isBlank()
            ) {
                if (binding.dataRTId.text.toString().isBlank())
                    binding.dataRTId.error = "fill id"

                if (binding.dataRTName.text.toString().equals(""))
                    binding.dataRTName.error = "fill name"

                showDialog("error")
            } else {
                dataRT.checkBox = false
                dataRT.id = Integer.parseInt(binding.dataRTId.text.toString())
                dataRT.name = binding.dataRTName.text.toString()


                for (s in checkBoxArray) {
                    dataRT.features += s
                    if (checkBoxArray.indexOf(s) < checkBoxArray.size - 1)
                        dataRT.features += ", "
                }

                mReferenceRT.child("${dataRT.id}").setValue(dataRT)
                showDialog("successful")
            }
        }

    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {

        var string: String
        when (buttonView!!.id) {
            R.id.cbFeature_aircon -> {
                string = binding.cbFeatureAircon.text.toString()
                if (isChecked)
                    checkBoxArray.add(string)
                else
                    checkBoxArray.remove(string)

            }
            R.id.cbFeature_breakfast -> {
                string = binding.cbFeatureBreakfast.text.toString()
                if (isChecked)
                    checkBoxArray.add(string)
                else checkBoxArray.remove(string)
            }
            R.id.cbFeature_news -> {
                string = binding.cbFeatureNews.text.toString()
                if (isChecked)
                    checkBoxArray.add(string)
                else checkBoxArray.remove(string)
            }
            R.id.cbFeature_powerbackup -> {
                string = binding.cbFeaturePowerbackup.text.toString()
                if (isChecked)
                    checkBoxArray.add(string)
                else checkBoxArray.remove(string)
            }
            R.id.cbFeature_swimmingpool -> {
                string = binding.cbFeatureSwimmingpool.text.toString()
                if (isChecked)
                    checkBoxArray.add(string)
                else checkBoxArray.remove(string)
            }
            R.id.cbFeature_wifi -> {
                string = binding.cbFeatureWifi.text.toString()
                if (isChecked)
                    checkBoxArray.add(string)
                else checkBoxArray.remove(string)
            }
        }
    }

    private fun getFireBaseData() {

        mReferenceBT = FirebaseDatabase.getInstance().getReference("bed_types")
        val childEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {

                for (data in p0!!.children) {
                    var item = data.getValue(Bed_Types_dt::class.java)
                    radioArray.add(item!!.name)
                }
                Toast.makeText(
                    activity?.applicationContext,
                    "size:${radioArray.size}",
                    Toast.LENGTH_SHORT
                ).show()

                var radioButton: RadioButton
                for (s in radioArray) {
                    radioButton = RadioButton(activity?.applicationContext)
                    radioButton.layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    radioButton.text = s
                    radioButton.setPadding(0, 10, 10, 10)
                    radioButton.id = radioArray.indexOf(s)
                    binding.radioGroupBT.addView(radioButton)

                }
            }


        }
        mReferenceBT.addListenerForSingleValueEvent(childEventListener)
    }

    private fun showDialog(s: String) {

        val builder = AlertDialog.Builder(activity)
        lateinit var dialogLayout: View
        when (s) {
            "error" -> dialogLayout = layoutInflater.inflate(R.layout.alert_error, null)
            "successful" -> dialogLayout = layoutInflater.inflate(R.layout.alert_success, null)
        }
        builder.setView(dialogLayout)
        var alertDialog = builder.show()


        when (s) {
            "error" ->  //try again
                dialogLayout.findViewById<Button>(R.id.btnAlert_tryagain).setOnClickListener {
                    alertDialog.dismiss()

                }
            "successful" ->  //continue
                dialogLayout.findViewById<Button>(R.id.btnAlert_continue).setOnClickListener {
                    alertDialog.dismiss()
                    activity?.finish()
                }
        }

    }

}
