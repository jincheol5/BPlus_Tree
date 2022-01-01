package b_plus;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NavigableSet;
import java.util.SortedSet;

import java.util.Queue;

@SuppressWarnings("unused")









class newiterator implements Iterator<Integer>{
	
	public SixWayBPlusTreeNode root;
	Queue<Integer> myq;
	Queue q;
	
	//생성자
	newiterator(SixWayBPlusTreeNode p){
		root=p;
		myq=new LinkedList<Integer>();
		q=showtreeup(root,myq);
		
		
	}
	
	
	
	public Queue showtreeup(SixWayBPlusTreeNode p,Queue q) {
		//오름차순 순서대로 큐에 삽입 
		if(p.children.isEmpty()) {
			//자식이 없으면 
			for(int i=0;i<p.keyList.size();i++) {
				
				q.offer((Integer)p.keyList.get(i));
				return q;
			}
		}
		else {
			
			for(int i=0;i<p.keyList.size();i++) {
				if(i==p.keyList.size()-1) {
					if(p.children.get(i)!=null) {
						return showtreeup((SixWayBPlusTreeNode)p.children.get(i),q);
					}
					System.out.println(p.keyList.get(i));
					if(p.children.get(i+1)!=null) {
						return showtreeup((SixWayBPlusTreeNode)p.children.get(i+1),q);
					}
				}
				else {
					if(p.children.get(i)!=null) {
						return showtreeup((SixWayBPlusTreeNode)p.children.get(i),q);
					}
					q.offer((Integer)p.keyList.get(i));
					return q;
				}
				
				
			}
			
			
		}
		return null;
		
		
		
	}
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		if(q==null) {
			return false;
		}
		if(q.peek()!=null) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Integer next() {
		// TODO Auto-generated method stub
		
		return (Integer)q.poll();
	}
	
}












public class SixWayBPlusTree implements NavigableSet<Integer> {

	// Data Abstraction은 예시일 뿐 자유롭게 B+ Tree의 범주 안에서 어느정도 수정가능
	public SixWayBPlusTreeNode root;
	public LinkedList<SixWayBPlusTreeNode> leafList;

	
	
	
	
	
	/*
	index로 보면
	left = 0 ~ ceiling((m-1)/2)-1
	middle = ceiling((m-1)/2)
	right = ceiling((m-1)/2)~(m-1)
	
	6way 에서는  
	left = 0 ~ 2
	middle = 3
	right = 4 ~ 5
	*/
	
	
	//생성자
	public SixWayBPlusTree() {
		root=new SixWayBPlusTreeNode();
	}
	
	
	//찾아가면서 출력하는 함수 
	public SixWayBPlusTreeNode find_print_node(SixWayBPlusTreeNode r,Integer dt) {
		
		//leaf까지 내려가기 
		
		if(r.children.isEmpty()) {
			//children node 없으면 = leaf node
			
			int jud=0;
			for(int i=0;i<r.keyList.size();i++) {
				if(r.keyList.get(i)==dt) jud=1;
				
				
			}
			
			if(jud==1) {
				
				System.out.println(dt+" found");
				return r;
			}
			else 
			{
				System.out.println(dt+" not found");
				return r;
			}
			
			
		}
		else {
			//children node 있으면
			
			
			int index=0;
					
			if(r.keyList.get(0)>dt) {
				//맨 첫번째 자식노드로 이동
				System.out.println("less than "+r.keyList.get(0));
				
				return find_print_node(r.children.get(0),dt);
			}
			else {
				
				//해당 위치의 자식 노드로 이동하거나 dt가 젤 클 경우에는 맨 마지막 자식노드로 이동
				int jud=0;
				
				index=r.keyList.size();
				for(int i=0;i<r.keyList.size();i++) {
					if(r.keyList.get(i)==dt) {
						jud=1;
						System.out.println("larger than or equal to "+r.keyList.get(i));
						index=i+1;
						break;
							
					}
					if(r.keyList.get(i)>dt) {
						jud=1;
						System.out.println("less than "+r.keyList.get(0));
						index=i;
						
						break;
							
					}
				}
				
			
				if(jud==0) {
					//dt가 keylist값들보다 크면 
					System.out.println("larger than or equal to "+r.keyList.get(index-1));
				}
				
				
				return find_print_node(r.children.get(index),dt);
			}
		}
		
		
		
	}
	
	//해당 값 찾아서 해당 노드 반환하는 함수
	public SixWayBPlusTreeNode getNode(Integer key) {
		return find_print_node(root,key);
		
	}
	
	
	//탐색 재귀 함수
	public boolean search(SixWayBPlusTreeNode r,Integer dt) {
		//맨 왼쪽 leaf까지 내려가서 해당 값 찾아본다 
		

		if(r.children.isEmpty()) {
			//children node 없으면 = leaf node 
			for(int i=0;i<r.keyList.size();i++) {
				
				if(r.keyList.get(i)==dt) {
					return true; //값 존재하면 true 반환 
				}
			}
			
			if(r.next_node==null) {
				//next node 도 없으면
				return false; //값이 없음
				
			}
			else {
				//next node 있으면
				return search(r.next_node,dt); //next node로 이동해서 값 찾기 
				
			}
			
			
		}
		else {
			//children node 있으면
			//가장 왼쪽 leaf 노드까지 이동하기 위해서 항상 맨 왼쪽 자식 노드로 이동
			return search(r.children.get(0),dt);
			
			

		}
	
		
		
	}
	
	//값이 들어갈 node를 찾아서 리턴하는 재귀함수
	public SixWayBPlusTreeNode findNode(SixWayBPlusTreeNode r,Integer dt) {
		
		
		//leaf까지 내려가기 
		
		if(r.children.isEmpty()) {
			//children node 없으면 = leaf node 
			
			return r;
			
		}
		else {
			//children node 있으면
			int index;
					
			if(r.keyList.get(0)>dt) {
				//맨 첫번째 자식노드로 이동 
				
				return findNode(r.children.get(0),dt);
			}
			else {
				
				//해당 위치의 자식 노드로 이동하거나 dt가 젤 클 경우에는 맨 마지막 자식노드로 이동
				
				index=r.keyList.size();
				for(int i=0;i<r.keyList.size();i++) {
					
					if(r.keyList.get(i)==dt) {
						index=i+1;
						break;
							
					}
					if(r.keyList.get(i)>dt) {
						index=i;
						break;
						
					}
				}
				
				
				return findNode(r.children.get(index),dt);
			}
		}
		
	}
	
	
	//해당 값을 가지고 있으면 true,없으면 false 반환 
	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		
		int dt=(Integer)o;
		
		if(search(root,dt))
			return true;
		else
			return false;
	}
	
	
	//leaf 노드 연결리스트 출력
	public void printTree(SixWayBPlusTreeNode node) {
		
		for(int i=0;i<node.keyList.size();i++) {
					
			System.out.println(node.keyList.get(i));
		}
				
		if(node.next_node!=null) {
			
			printTree(node.next_node);
		}
		
		
	}
	
	//첫 번째 leaf 노드 찾아서 반환 
	public SixWayBPlusTreeNode find_first(SixWayBPlusTreeNode r){
		
		if(r.children.isEmpty()) {
			//children node 없으면 = leaf node 
			
			
			return r;
		}
		else {
			//children node 있으면
			//가장 왼쪽 leaf 노드까지 이동하기 위해서 항상 맨 왼쪽 자식 노드로 이동
			return find_first(r.children.get(0));
	
		}
		
	}
	
	//마지막 leaf 노드 찾아서 반환 
	public SixWayBPlusTreeNode find_last(SixWayBPlusTreeNode r) {
		
		if(r.next_node!=null) {
			return find_last(r.next_node);
		}
		else {
			return r;
		}
		
	}
	
	
	//leaf 노드 순서대로 출력 
	public void inorderTraverse() {
		// TODO Auto-generated method stub
		//중위 순회
		//트리에 값 들어가 있는지 노드 전부 출력해보기 위한 함수
		
		SixWayBPlusTreeNode node=find_first(root);
		
		
		printTree(node);
		
	}
	
	
	
	
	
	
	@Override
	public boolean add(Integer e) {
		// TODO Auto-generated method stub
		
		if(contains(e)==true) {
			//중복 값일 경우 add 종료 
			System.out.println("중복 값이여서 입력 취소");
			return false;
		}
		else {
			//System.out.println(e+" 입력");
			
			SixWayBPlusTreeNode node=findNode(root,e);
			//해당 leaf 노드가 온다
			
			
			
			node.keyList.add(e);
			
			Collections.sort(node.keyList); //오름차순 정렬 
			
			
			
			
			if(node.keyList.size()==6) {
				
				
				separation(node);
			}

			return true;
		}
		

		
	}
	
	
	
	//leaf node에서 key가 6개 일 때 분리 함수 
	public void separation(SixWayBPlusTreeNode r) {
		
		//항상 leaf 노드가 들어온다
		//leaf 노드에서 분리 할 때 = 처음 루트 이거나 리프노드
		
		SixWayBPlusTreeNode p=new SixWayBPlusTreeNode();
		SixWayBPlusTreeNode c_left=new SixWayBPlusTreeNode();
		SixWayBPlusTreeNode c_right=new SixWayBPlusTreeNode();

		
			
		if(r.parent==null) {
			//처음 루트에서 분리 할 때
			
			
			p.keyList.add(r.keyList.get(3)); //부모
			
			
			for(int i=0;i<3;i++) {
				//왼쪽 자식
				c_left.keyList.add(r.keyList.get(i));
				
			}
			
			for(int i=3;i<r.keyList.size();i++) {
				//오른쪽 자식, 4번째 값 복제 
				c_right.keyList.add(r.keyList.get(i));
				
			}
			
			p.children.add(c_left);
			p.children.add(c_right);
			
			c_left.parent=p;
			c_left.parent_index=0;
			
			c_right.parent=p;
			c_right.parent_index=1;
			
			c_left.next_node=c_right; //노드 연결
			
			root=p;
			
			
			
			
		}
		else {
			//root가 아닌 leaf node 일 때
			
			
			
			for(int i=0;i<3;i++) {
				//왼쪽 자식
				c_left.keyList.add(r.keyList.get(i));
				
			}
			
			for(int i=3;i<r.keyList.size();i++) {
				//오른쪽 자식, 4번째 값 복제 
				c_right.keyList.add(r.keyList.get(i));
				
			}
			
			
			r.parent.keyList.add(r.parent_index, r.keyList.get(3)); //parent node에 4번째 keyList 값을 올린다.
			
			r.parent.children.set(r.parent_index,c_left); //parent node children 리스트에서 parent_index 위치에 c_left 설정 = 기존 r 이 있던 자리 
			r.parent.children.add(r.parent_index+1,c_right); //그 옆에 c_right를 끼워넣기 -> 뒤에 있던 children들 한칸씩 밀림 
			
			
		
			
			if(r.parent.children.size()>r.parent_index+2) {
				
				//그 옆에 밀린 children들이 있을 경우 해당 children들의 parent_index들도 바꿔준다.
				for(int i=r.parent_index+2;i<r.parent.children.size();i++) {
					
					r.parent.children.get(i).parent_index+=1;
					
				}
			
			}
		
			c_left.parent=r.parent;
			c_left.parent_index=r.parent_index;
			
			c_right.parent=r.parent;
			c_right.parent_index=r.parent_index+1;
			
			
			if(r.parent_index!=0) {
				//leaf 노드가 맨 왼쪽이 아니라면 그 전 leaf 노드와 연결시켜준다
				r.parent.children.get(r.parent_index-1).next_node=c_left;
			}
			if(r.parent.keyList.size()>r.parent_index+1) {
				//leaf 노드 오른쪽에 노드가 있었다면
				c_right.next_node=r.parent.children.get(r.parent_index+2);
			}
			c_left.next_node=c_right; //노드 연결
			
			reseparation(r.parent);
			
			
		}
		
			

		
		
		

		
		
		
	}
	
	//children 있는 root,leaf,중간 노드 분리 재귀 함수 
	public void reseparation(SixWayBPlusTreeNode r) {
		
		if(r.keyList.size()!=6) {
			//분리 안해도 되면 종료
			return;
		}
		
		
		SixWayBPlusTreeNode p=new SixWayBPlusTreeNode();
		SixWayBPlusTreeNode c_left=new SixWayBPlusTreeNode();
		SixWayBPlusTreeNode c_right=new SixWayBPlusTreeNode();
		
		
		
		if(r.parent==null) {
			//children 있는 root
			
			p.keyList.add(r.keyList.get(3)); //부모
			
			
			
			//keyList 나누어서 보내기 
			for(int i=0;i<3;i++) {
				//왼쪽 자식
				c_left.keyList.add(r.keyList.get(i));
				
			}
			
			for(int i=4;i<r.keyList.size();i++) {
				//오른쪽 자식
				c_right.keyList.add(r.keyList.get(i));
				
				
			}
			
			
			
			//자식 노드들 나누어서 보내기 
			for(int i=0;i<4;i++) {
				//왼쪽 자식노드들 모두 c_left로 
				c_left.children.add(r.children.get(i));
				c_left.children.get(i).parent_index=i;
				
			}
			
			for(int i=4,index=0;i<7;i++,index++) {
				//오른쪽 자식노드들 모두 c_right로
				c_right.children.add(r.children.get(i));
				c_right.children.get(index).parent_index=index;
				
			}
			
			p.children.add(c_left);
			p.children.add(c_right);
			
			c_left.parent=p;
			c_right.parent=p;
			
			
			c_left.parent_index=0;
			c_right.parent_index=1;
			
			root=p;
			
			
			
		}
		else 
		{
			//중간 노드 이면 
			
			//keyList 나누어서 보내기 
			for(int i=0;i<3;i++) {
				//왼쪽 자식
				c_left.keyList.add(r.keyList.get(i));
				
			}
			
			for(int i=4;i<r.keyList.size();i++) {
				//오른쪽 자식
				c_right.keyList.add(r.keyList.get(i));
				
				
			}
			
			
			
			//자식 노드들 나누어서 보내기 
			for(int i=0;i<4;i++) {
				//왼쪽 자식노드들 모두 c_left로 
				c_left.children.add(r.children.get(i));
				c_left.children.get(i).parent_index=i;
				
			}
			
			for(int i=4,index=0;i<7;i++,index++) {
				//오른쪽 자식노드들 모두 c_right로
				c_right.children.add(r.children.get(i));
				c_right.children.get(index).parent_index=index;
				
			}
			
			
			//부모 노드로 값 올리기 
			r.parent.keyList.add(r.parent_index, r.keyList.get(3)); //parent node에 4번째 keyList 값을 올린다.
			
			r.parent.children.set(r.parent_index,c_left); //parent node children 리스트에서 parent_index 위치에 c_left 설정 = 기존 r 이 있던 자리 
			r.parent.children.add(r.parent_index+1,c_right); //그 옆에 c_right를 끼워넣기 -> 뒤에 있던 children들 한칸씩 밀림 
			
			
			//부모 노드의 children들의 parent_index 조정
			if(r.parent.children.size()>r.parent_index+2) {
				
				//그 옆에 밀린 children들이 있을 경우 해당 children들의 parent_index들도 바꿔준다.
				for(int i=r.parent_index+2;i<r.parent.children.size();i++) {
					
					r.parent.children.get(i).parent_index+=1;
					
				}
				
			}
			
			
			
			c_left.parent=r.parent;
			c_left.parent_index=r.parent_index;
			
			c_right.parent=r.parent;
			c_right.parent_index=r.parent_index+1;
			
			
			reseparation(r.parent);
		}
			
			
		
		

	}
	
	
	@Override
	public Integer first() {
		// TODO Auto-generated method stub
		SixWayBPlusTreeNode node=find_first(root);
		
		
		
		
		return node.keyList.get(0);
	}

	@Override
	public Integer last() {
		// TODO Auto-generated method stub
		
		
		SixWayBPlusTreeNode node=find_last(find_first(root));
		
		
		
		
		return node.keyList.get(node.keyList.size()-1);
	}
	
	
	@Override
	public Iterator<Integer> iterator() {
		if(root==null) {
			return null;
		}
		return new newiterator(root);
		
	}

	
	
	

	
	

	@Override
	public Comparator<? super Integer> comparator() {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends Integer> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer lower(Integer e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer floor(Integer e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer ceiling(Integer e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer higher(Integer e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer pollFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer pollLast() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public NavigableSet<Integer> descendingSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<Integer> descendingIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NavigableSet<Integer> subSet(Integer fromElement, boolean fromInclusive, Integer toElement,
			boolean toInclusive) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NavigableSet<Integer> headSet(Integer toElement, boolean inclusive) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NavigableSet<Integer> tailSet(Integer fromElement, boolean inclusive) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SortedSet<Integer> subSet(Integer fromElement, Integer toElement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SortedSet<Integer> headSet(Integer toElement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SortedSet<Integer> tailSet(Integer fromElement) {
		// TODO Auto-generated method stub
		return null;
	}

}
