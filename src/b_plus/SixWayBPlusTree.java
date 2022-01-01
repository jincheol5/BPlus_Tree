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
	
	//������
	newiterator(SixWayBPlusTreeNode p){
		root=p;
		myq=new LinkedList<Integer>();
		q=showtreeup(root,myq);
		
		
	}
	
	
	
	public Queue showtreeup(SixWayBPlusTreeNode p,Queue q) {
		//�������� ������� ť�� ���� 
		if(p.children.isEmpty()) {
			//�ڽ��� ������ 
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

	// Data Abstraction�� ������ �� �����Ӱ� B+ Tree�� ���� �ȿ��� ������� ��������
	public SixWayBPlusTreeNode root;
	public LinkedList<SixWayBPlusTreeNode> leafList;

	
	
	
	
	
	/*
	index�� ����
	left = 0 ~ ceiling((m-1)/2)-1
	middle = ceiling((m-1)/2)
	right = ceiling((m-1)/2)~(m-1)
	
	6way ������  
	left = 0 ~ 2
	middle = 3
	right = 4 ~ 5
	*/
	
	
	//������
	public SixWayBPlusTree() {
		root=new SixWayBPlusTreeNode();
	}
	
	
	//ã�ư��鼭 ����ϴ� �Լ� 
	public SixWayBPlusTreeNode find_print_node(SixWayBPlusTreeNode r,Integer dt) {
		
		//leaf���� �������� 
		
		if(r.children.isEmpty()) {
			//children node ������ = leaf node
			
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
			//children node ������
			
			
			int index=0;
					
			if(r.keyList.get(0)>dt) {
				//�� ù��° �ڽĳ��� �̵�
				System.out.println("less than "+r.keyList.get(0));
				
				return find_print_node(r.children.get(0),dt);
			}
			else {
				
				//�ش� ��ġ�� �ڽ� ���� �̵��ϰų� dt�� �� Ŭ ��쿡�� �� ������ �ڽĳ��� �̵�
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
					//dt�� keylist���麸�� ũ�� 
					System.out.println("larger than or equal to "+r.keyList.get(index-1));
				}
				
				
				return find_print_node(r.children.get(index),dt);
			}
		}
		
		
		
	}
	
	//�ش� �� ã�Ƽ� �ش� ��� ��ȯ�ϴ� �Լ�
	public SixWayBPlusTreeNode getNode(Integer key) {
		return find_print_node(root,key);
		
	}
	
	
	//Ž�� ��� �Լ�
	public boolean search(SixWayBPlusTreeNode r,Integer dt) {
		//�� ���� leaf���� �������� �ش� �� ã�ƺ��� 
		

		if(r.children.isEmpty()) {
			//children node ������ = leaf node 
			for(int i=0;i<r.keyList.size();i++) {
				
				if(r.keyList.get(i)==dt) {
					return true; //�� �����ϸ� true ��ȯ 
				}
			}
			
			if(r.next_node==null) {
				//next node �� ������
				return false; //���� ����
				
			}
			else {
				//next node ������
				return search(r.next_node,dt); //next node�� �̵��ؼ� �� ã�� 
				
			}
			
			
		}
		else {
			//children node ������
			//���� ���� leaf ������ �̵��ϱ� ���ؼ� �׻� �� ���� �ڽ� ���� �̵�
			return search(r.children.get(0),dt);
			
			

		}
	
		
		
	}
	
	//���� �� node�� ã�Ƽ� �����ϴ� ����Լ�
	public SixWayBPlusTreeNode findNode(SixWayBPlusTreeNode r,Integer dt) {
		
		
		//leaf���� �������� 
		
		if(r.children.isEmpty()) {
			//children node ������ = leaf node 
			
			return r;
			
		}
		else {
			//children node ������
			int index;
					
			if(r.keyList.get(0)>dt) {
				//�� ù��° �ڽĳ��� �̵� 
				
				return findNode(r.children.get(0),dt);
			}
			else {
				
				//�ش� ��ġ�� �ڽ� ���� �̵��ϰų� dt�� �� Ŭ ��쿡�� �� ������ �ڽĳ��� �̵�
				
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
	
	
	//�ش� ���� ������ ������ true,������ false ��ȯ 
	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		
		int dt=(Integer)o;
		
		if(search(root,dt))
			return true;
		else
			return false;
	}
	
	
	//leaf ��� ���Ḯ��Ʈ ���
	public void printTree(SixWayBPlusTreeNode node) {
		
		for(int i=0;i<node.keyList.size();i++) {
					
			System.out.println(node.keyList.get(i));
		}
				
		if(node.next_node!=null) {
			
			printTree(node.next_node);
		}
		
		
	}
	
	//ù ��° leaf ��� ã�Ƽ� ��ȯ 
	public SixWayBPlusTreeNode find_first(SixWayBPlusTreeNode r){
		
		if(r.children.isEmpty()) {
			//children node ������ = leaf node 
			
			
			return r;
		}
		else {
			//children node ������
			//���� ���� leaf ������ �̵��ϱ� ���ؼ� �׻� �� ���� �ڽ� ���� �̵�
			return find_first(r.children.get(0));
	
		}
		
	}
	
	//������ leaf ��� ã�Ƽ� ��ȯ 
	public SixWayBPlusTreeNode find_last(SixWayBPlusTreeNode r) {
		
		if(r.next_node!=null) {
			return find_last(r.next_node);
		}
		else {
			return r;
		}
		
	}
	
	
	//leaf ��� ������� ��� 
	public void inorderTraverse() {
		// TODO Auto-generated method stub
		//���� ��ȸ
		//Ʈ���� �� �� �ִ��� ��� ���� ����غ��� ���� �Լ�
		
		SixWayBPlusTreeNode node=find_first(root);
		
		
		printTree(node);
		
	}
	
	
	
	
	
	
	@Override
	public boolean add(Integer e) {
		// TODO Auto-generated method stub
		
		if(contains(e)==true) {
			//�ߺ� ���� ��� add ���� 
			System.out.println("�ߺ� ���̿��� �Է� ���");
			return false;
		}
		else {
			//System.out.println(e+" �Է�");
			
			SixWayBPlusTreeNode node=findNode(root,e);
			//�ش� leaf ��尡 �´�
			
			
			
			node.keyList.add(e);
			
			Collections.sort(node.keyList); //�������� ���� 
			
			
			
			
			if(node.keyList.size()==6) {
				
				
				separation(node);
			}

			return true;
		}
		

		
	}
	
	
	
	//leaf node���� key�� 6�� �� �� �и� �Լ� 
	public void separation(SixWayBPlusTreeNode r) {
		
		//�׻� leaf ��尡 ���´�
		//leaf ��忡�� �и� �� �� = ó�� ��Ʈ �̰ų� �������
		
		SixWayBPlusTreeNode p=new SixWayBPlusTreeNode();
		SixWayBPlusTreeNode c_left=new SixWayBPlusTreeNode();
		SixWayBPlusTreeNode c_right=new SixWayBPlusTreeNode();

		
			
		if(r.parent==null) {
			//ó�� ��Ʈ���� �и� �� ��
			
			
			p.keyList.add(r.keyList.get(3)); //�θ�
			
			
			for(int i=0;i<3;i++) {
				//���� �ڽ�
				c_left.keyList.add(r.keyList.get(i));
				
			}
			
			for(int i=3;i<r.keyList.size();i++) {
				//������ �ڽ�, 4��° �� ���� 
				c_right.keyList.add(r.keyList.get(i));
				
			}
			
			p.children.add(c_left);
			p.children.add(c_right);
			
			c_left.parent=p;
			c_left.parent_index=0;
			
			c_right.parent=p;
			c_right.parent_index=1;
			
			c_left.next_node=c_right; //��� ����
			
			root=p;
			
			
			
			
		}
		else {
			//root�� �ƴ� leaf node �� ��
			
			
			
			for(int i=0;i<3;i++) {
				//���� �ڽ�
				c_left.keyList.add(r.keyList.get(i));
				
			}
			
			for(int i=3;i<r.keyList.size();i++) {
				//������ �ڽ�, 4��° �� ���� 
				c_right.keyList.add(r.keyList.get(i));
				
			}
			
			
			r.parent.keyList.add(r.parent_index, r.keyList.get(3)); //parent node�� 4��° keyList ���� �ø���.
			
			r.parent.children.set(r.parent_index,c_left); //parent node children ����Ʈ���� parent_index ��ġ�� c_left ���� = ���� r �� �ִ� �ڸ� 
			r.parent.children.add(r.parent_index+1,c_right); //�� ���� c_right�� �����ֱ� -> �ڿ� �ִ� children�� ��ĭ�� �и� 
			
			
		
			
			if(r.parent.children.size()>r.parent_index+2) {
				
				//�� ���� �и� children���� ���� ��� �ش� children���� parent_index�鵵 �ٲ��ش�.
				for(int i=r.parent_index+2;i<r.parent.children.size();i++) {
					
					r.parent.children.get(i).parent_index+=1;
					
				}
			
			}
		
			c_left.parent=r.parent;
			c_left.parent_index=r.parent_index;
			
			c_right.parent=r.parent;
			c_right.parent_index=r.parent_index+1;
			
			
			if(r.parent_index!=0) {
				//leaf ��尡 �� ������ �ƴ϶�� �� �� leaf ���� ��������ش�
				r.parent.children.get(r.parent_index-1).next_node=c_left;
			}
			if(r.parent.keyList.size()>r.parent_index+1) {
				//leaf ��� �����ʿ� ��尡 �־��ٸ�
				c_right.next_node=r.parent.children.get(r.parent_index+2);
			}
			c_left.next_node=c_right; //��� ����
			
			reseparation(r.parent);
			
			
		}
		
			

		
		
		

		
		
		
	}
	
	//children �ִ� root,leaf,�߰� ��� �и� ��� �Լ� 
	public void reseparation(SixWayBPlusTreeNode r) {
		
		if(r.keyList.size()!=6) {
			//�и� ���ص� �Ǹ� ����
			return;
		}
		
		
		SixWayBPlusTreeNode p=new SixWayBPlusTreeNode();
		SixWayBPlusTreeNode c_left=new SixWayBPlusTreeNode();
		SixWayBPlusTreeNode c_right=new SixWayBPlusTreeNode();
		
		
		
		if(r.parent==null) {
			//children �ִ� root
			
			p.keyList.add(r.keyList.get(3)); //�θ�
			
			
			
			//keyList ����� ������ 
			for(int i=0;i<3;i++) {
				//���� �ڽ�
				c_left.keyList.add(r.keyList.get(i));
				
			}
			
			for(int i=4;i<r.keyList.size();i++) {
				//������ �ڽ�
				c_right.keyList.add(r.keyList.get(i));
				
				
			}
			
			
			
			//�ڽ� ���� ����� ������ 
			for(int i=0;i<4;i++) {
				//���� �ڽĳ��� ��� c_left�� 
				c_left.children.add(r.children.get(i));
				c_left.children.get(i).parent_index=i;
				
			}
			
			for(int i=4,index=0;i<7;i++,index++) {
				//������ �ڽĳ��� ��� c_right��
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
			//�߰� ��� �̸� 
			
			//keyList ����� ������ 
			for(int i=0;i<3;i++) {
				//���� �ڽ�
				c_left.keyList.add(r.keyList.get(i));
				
			}
			
			for(int i=4;i<r.keyList.size();i++) {
				//������ �ڽ�
				c_right.keyList.add(r.keyList.get(i));
				
				
			}
			
			
			
			//�ڽ� ���� ����� ������ 
			for(int i=0;i<4;i++) {
				//���� �ڽĳ��� ��� c_left�� 
				c_left.children.add(r.children.get(i));
				c_left.children.get(i).parent_index=i;
				
			}
			
			for(int i=4,index=0;i<7;i++,index++) {
				//������ �ڽĳ��� ��� c_right��
				c_right.children.add(r.children.get(i));
				c_right.children.get(index).parent_index=index;
				
			}
			
			
			//�θ� ���� �� �ø��� 
			r.parent.keyList.add(r.parent_index, r.keyList.get(3)); //parent node�� 4��° keyList ���� �ø���.
			
			r.parent.children.set(r.parent_index,c_left); //parent node children ����Ʈ���� parent_index ��ġ�� c_left ���� = ���� r �� �ִ� �ڸ� 
			r.parent.children.add(r.parent_index+1,c_right); //�� ���� c_right�� �����ֱ� -> �ڿ� �ִ� children�� ��ĭ�� �и� 
			
			
			//�θ� ����� children���� parent_index ����
			if(r.parent.children.size()>r.parent_index+2) {
				
				//�� ���� �и� children���� ���� ��� �ش� children���� parent_index�鵵 �ٲ��ش�.
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
