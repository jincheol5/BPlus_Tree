package b_plus;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class SixWayBPlusTreeNode {

	// Data Abstraction은 예시일 뿐 자유롭게 B+ Tree의 범주 안에서 어느정도 수정가능
	public SixWayBPlusTreeNode parent;
	public int parent_index; //자신이 부모 노드의 children 에서 어느 인덱스에 있는지 
	public SixWayBPlusTreeNode next_node; //leaf node linked list 
	public List<Integer> keyList;
	public List<SixWayBPlusTreeNode> children;
	
	
	
	
	
	//생성자 
	public SixWayBPlusTreeNode() {
		
		
		parent=null;
		
		parent_index=-1;
		
		next_node=null;
		
		keyList=new ArrayList<Integer>();
		children=new ArrayList<SixWayBPlusTreeNode>();
		
		
		
		
		
		
		
		
	}
	
	
	//root 노드인지 아닌지 판단 함수, root노드이면 true, 아니면 false  반환 
	public boolean jud_root() {
		if(parent==null) 
			return true;
		else 
			return false;
	}
	
	
	
	//key가 6개일 경우(분리해야할 경우) true, 아닐 시 false 반환 함수 
	public boolean jud_keyListsize() {
		if(keyList.size()==6) {
			return true;
		}
		else
		{
			return false;
		}
	}

	
	//keyList에서 해당 값이 몇번째에 위치해있는지 확인 
	public int find_keyindex(Integer e) {
		int index=0;
		for(int i=0;i<keyList.size();i++) {
			if(keyList.get(i)==e) {
				index=i;
			}
		}
		return index;
	}
	
	
	
	
	//children list 에서 null값이 나타나기 전의 인덱스 반환
	public int return_children_index() {
		int count=0;
		for(int i=0;i<7;i++) {
			
			if(children.get(i)!=null) {
				
				count++;
				
			}
			else {
				
				break;
			}
		}
		return count;
	}
	
}