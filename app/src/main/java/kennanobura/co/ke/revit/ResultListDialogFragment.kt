package kennanobura.co.ke.revit

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kennanobura.co.ke.revit.MainActivity.Companion.LIST_ITEM
import kotlinx.android.synthetic.main.fragment_item_list_dialog.*
import kotlinx.android.synthetic.main.fragment_item_list_dialog_item.view.*


class ResultListDialogFragment : BottomSheetDialogFragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var resultListAdapter: ResultListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_item_list_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = this.rv_list
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        resultListAdapter = ResultListAdapter(emptyList())

        resultListAdapter.update(LIST_ITEM)

        recyclerView.adapter = resultListAdapter
    }


}
