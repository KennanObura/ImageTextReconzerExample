package kennanobura.co.ke.revit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_item_list_dialog_item.view.*

class ResultListAdapter(var item: List<ResultModel>) : RecyclerView.Adapter<ResultListAdapter.ViewHolder>(){


    fun update(data: List<ResultModel>){
        item = data

        notifyDataSetChanged()

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultListAdapter.ViewHolder {

        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_item_list_dialog_item, parent, false))

    }

    override fun getItemCount(): Int = item.size

    override fun getItemViewType(position: Int): Int = position

    override fun onBindViewHolder(holder: ResultListAdapter.ViewHolder, position: Int) {

        holder.bind(item[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ResultModel){
            itemView.text_result.text = item.text
        }

    }
}