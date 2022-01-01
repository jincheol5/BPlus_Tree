package b_plus;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class SixWayBPlusTreeNode {

	// Data Abstraction�� ������ �� �����Ӱ� B+ Tree�� ���� �ȿ��� ������� ��������
	public SixWayBPlusTreeNode parent;
	public int parent_index; //�ڽ��� �θ� ����� children ���� ��� �ε����� �ִ��� 
	public SixWayBPlusTreeNode next_node; //leaf node linked list 
	public List<Integer> keyList;
	public List<SixWayBPlusTreeNode> children;
	
	
	
	
	
	//������ 
	public SixWayBPlusTreeNode() {
		
		
		parent=null;
		
		parent_index=-1;
		
		next_node=null;
		
		keyList=new ArrayList<Integer>();
		children=new ArrayList<SixWayBPlusTreeNode>();
		
		
		
		
		
		
		
		
	}
	
	
	//root ������� �ƴ��� �Ǵ� �Լ�, root����̸� true, �ƴϸ� false  ��ȯ 
	public boolean jud_root() {
		if(parent==null) 
			return true;
		else 
			return false;
	}
	
	
	
	//key�� 6���� ���(�и��ؾ��� ���) true, �ƴ� �� false ��ȯ �Լ� 
	public boolean jud_keyListsize() {
		if(keyList.size()==6) {
			return true;
		}
		else
		{
			return false;
		}
	}

	
	//keyList���� �ش� ���� ���°�� ��ġ���ִ��� Ȯ�� 
	public int find_keyindex(Integer e) {
		int index=0;
		for(int i=0;i<keyList.size();i++) {
			if(keyList.get(i)==e) {
				index=i;
			}
		}
		return index;
	}
	
	
	
	
	//children list ���� null���� ��Ÿ���� ���� �ε��� ��ȯ
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