package com.faresa.githubsearchuser.pojo.search;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SearchResponse{

	@SerializedName("total_count")
	private int totalCount;

	@SerializedName("incomplete_results")
	private boolean incompleteResults;

	@SerializedName("items")
	private List<SearchData> items;

	public void setTotalCount(int totalCount){
		this.totalCount = totalCount;
	}

	public int getTotalCount(){
		return totalCount;
	}

	public void setIncompleteResults(boolean incompleteResults){
		this.incompleteResults = incompleteResults;
	}

	public boolean isIncompleteResults(){
		return incompleteResults;
	}

	public void setItems(List<SearchData> items){
		this.items = items;
	}

	public List<SearchData> getItems(){
		return items;
	}

	@Override
 	public String toString(){
		return 
			"SearchResponse{" + 
			"total_count = '" + totalCount + '\'' + 
			",incomplete_results = '" + incompleteResults + '\'' + 
			",items = '" + items + '\'' + 
			"}";
		}
}