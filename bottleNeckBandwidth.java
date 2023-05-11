import java.util.*;
 
public class MST {
	
	//inner class for vertex
	public static class Vertex {
		private int ID;
		private int priority;
		private String name;
		private Map<String, Integer> adjVertex;

		
		//Constructor to create a new vertex by giving id and name
		
		public Vertex(int id, String name) {
			if (name.equals(null)) {
				System.out.println("The name is null, please try again.");
			}
			else {
				this.ID = id;
				this.name = name;
				this.priority = 0;
				adjVertex = new HashMap<>();
			}
		}
	}
	
	//class variables
	private ArrayList <Vertex> vertices;
	private ArrayList <Vertex> record;
	private ArrayList <Vertex> removed;
	private Map<String, Integer> vId;
	private int minBandwidth = Integer.MAX_VALUE;
	private int ID;

	
	public MST() {
		vertices = new ArrayList<>();
		record = new ArrayList<>();
		removed = new ArrayList<>();
		vId = new HashMap<>();
		ID = 0;
	}
	//Method to add a edge to the class
	public boolean addEdge(String name1, String name2, int weight) {
		if ((!this.vId.containsKey(name1)) && (!this.vId.containsKey(name1))) {
			this.add(name1, name2, weight);
			this.add(name2, name1, weight);
			
			return true;
		}
		else if (this.vId.containsKey(name1) && !this.vId.containsKey(name2)) {
			this.add(name2, name1, weight);
			
			return true;
		}
		else if (!this.vId.containsKey(name1) && this.vId.containsKey(name2)) {
			this.add(name1, name2, weight);
			
			return true;
		}
		else {
			vertices.get(vId.get(name1)).adjVertex.put(name2, weight);
			vertices.get(vId.get(name2)).adjVertex.put(name1, weight);
			return true;
		}
	}
	
	//Methods to add a vertex based on given edge and weight
	public boolean add(String name1, String name2, int weight) {
		vId.put(name1, ID);
		Vertex newNode = new Vertex(this.ID, name1);
			
		if (vId.containsKey(name2)) {
			Vertex newNode2 = record.get(vId.get(name2));
			newNode2.adjVertex.put(name1, weight);
			vertices.set(vId.get(name2), newNode2);
			record.set(vId.get(name2), newNode2);
		}
			
		newNode.adjVertex.put(name2, weight);
		vertices.add(newNode);
		record.add(newNode);
		ID++;
		
		return true;
	}
	//method to remove the a vertex at certain position and reheap
	public Vertex remove(int index) {
			
		Vertex remove = vertices.get(index);
			
		if (vertices.size() == 1) {
			vertices.remove(0);
				
			return remove;
		}
		else {
			vertices.set(index,vertices.get(vertices.size() - 1));
			vertices.remove(vertices.size() - 1);
				
			int i = index + 1;
			while(i < vertices.size()) { 
				Vertex current = vertices.get(i - 1);
				Vertex tempNode = current;
				if ((i * 2 + 1) <= vertices.size()) {
					Vertex left = vertices.get(2*i - 1);
					Vertex right = vertices.get(2*i);
					
					if (current.priority == left.priority) {
						if (current.priority == right.priority) {
							if (current.ID > left.ID) {
								if (current.ID > right.ID) {
									if (left.ID > right.ID) {
										vertices.set(i - 1, right);
										vertices.set(2 * i, tempNode);
										i = 2 * i + 1;
									}//end if (currentP=left: currentP = right: current time after left: current time after right: left time after right)
									else {
										vertices.set(i - 1, left);
										vertices.set(2 * i - 1, tempNode);
										i = 2 * i;
									}//end if (currentP=left: currentP = right: current time after left: current time after right: left time before right)
								}//end if (currentP=left: currentP = right: current time after left : current time after right)
								else {
									vertices.set(i - 1, left);
									vertices.set(2 * i - 1, tempNode);
									i = 2 * i;
								}//end else (currentP=left: currentP = right: current time after left: current time before right)
							}//end if (currentP=left: currentP = right: current time after left)
							else if (current.ID > right.ID) {
								vertices.set(i - 1, right);
								vertices.set(2 * i, tempNode);
								i = 2 * i + 1;
							}//end else if (currentP=left: currentP = right: current time before left: current time after right)
							else {
								
								return remove;
							}//end else (currentP=left: currentP = right: current time before left: current time before right)
						}//end if (currentP=left: currentP = right)
						else if (current.priority < right.priority) {
							vertices.set(i - 1, right);
							vertices.set(2 * i, tempNode);
							i = 2 * i + 1;
						}// end else if (current P = left: currentP < right)
						else {
							if (current.ID > left.ID) {
								vertices.set(i - 1, left);
								vertices.set(2 * i - 1, tempNode);
								i = 2 * i;
							}// end if (current P = left: currentP > right: current time after left)
							else {
								
								return remove;
							}// end else (current P = left: currentP > right: current time before left)
						}// end else (current P = left: currentP > right)
					}//end if (current P = left)
					else if (current.priority < left.priority) {
						if (current.priority < right.priority) {
							if (left.priority == right.priority) {
								if (left.ID > right.ID) {
									vertices.set(i - 1, right);
									vertices.set(2 * i, tempNode);
									i = 2 * i + 1;
								}//end if (current P < left: current P < right: left P = right: left time after right)
								else {
									vertices.set(i - 1, left);
									vertices.set(2 * i - 1, tempNode);
									i = 2 * i;
								}//end else (current P < left: current P < right: left P = right: left time before right)
							}//end if (current P < left: current P < right: left P = right)
							else if (left.priority < right.priority) {
								vertices.set(i - 1, right);
								vertices.set(2 * i, tempNode);
								i = 2 * i + 1;
							}//end if (current P < left: current P < right: left P < right)
							else {
								vertices.set(i - 1, left);
								vertices.set(2 * i - 1, tempNode);
								i = 2 * i;
							}//end else (current P < left: current P < right: left P > right)
						}//end else if (current P < left: current P < right)
						else {
							vertices.set(i - 1, left);
							vertices.set(2 * i - 1, tempNode);
							i = 2 * i;
						}//end else (current P < left: current >= right)
					}//end else if (current P < left)
					else {
						if (current.priority == right.priority) {
							if (current.ID > right.ID) {
								vertices.set(i - 1, right);
								vertices.set(2 * i, tempNode);
								i = 2 * i + 1;
							}//end if (current P > left: current P = right: current time after right)
							else {
								
								return remove;
							}//end else //end if (current P > left: current P = right: current time before right)
						}//end if (current P > left: current P = right)
						else if (current.priority < right.priority) {
							vertices.set(i - 1, right);
							vertices.set(2 * i, tempNode);
							i = 2 * i + 1;
						}//end else if (current P > left: current P < right)
						else {
							
							return remove;
						}//end else if (current P > left: current P > right)
					}//end else (current P > left)
				}// end if current node has two children
				else {
					if ((2 * i) == vertices.size()) {
						Vertex left = vertices.get(2*i - 1);
						if (current.priority == left.priority) {
							if (current.ID > left.ID) {
								vertices.set(i - 1, left);
								vertices.set(2 * i - 1, tempNode);
								i = 2 * i;
							}//end if (current P = left: current time after left)
							else {
								
								return remove;
							}//end else (current P = left: current time before left)
						}//end if (current P = left)
						else if (current.priority < left.priority) {
							vertices.set(i - 1, left);
							vertices.set(2 * i - 1, tempNode);
							i = 2 * i;
						}//end if (current P < left)
						else {
							
							return remove;
						}
					}//end if current node only has left children
					
					return remove;
				}//end else (current node has zero or only left children)
			}//end while
			
			return remove;
		}//end else (size >1)
	}//end remove()
	
	//Method to find the index for the vertex in vertices given name
	public int findIndex(String a) {
		int i;
		for (i = 0; i < vertices.size(); ++i) {
			if (vertices.get(i).name.equals(a)) {
				return i;
			}
		}
		
		return i;
	}
	
	//Method to update the priority in the heap
	public void updatePriority(int index, int p) {
		vertices.get(index).priority = p;
		int i = index + 1;
		if (vertices.size() > 1 && index != 0) {
			while(i > 1 && vertices.get(i - 1).priority >= vertices.get(i/2 - 1).priority) {
				if (vertices.get(i - 1).priority == vertices.get(i/2 - 1).priority) {
					if (vertices.get(i - 1).ID > vertices.get(i/2 - 1).ID){
						Vertex tempNode = vertices.get(i/2 - 1);
						vertices.set(i/2 - 1, vertices.get(i - 1));
						vertices.set(i - 1, tempNode);
						i = i / 2;
					}//end inner if
					
				}//end outer if
				else {
					Vertex tempNode = vertices.get(i/2 - 1);
					vertices.set(i/2 - 1, vertices.get(i - 1));
					vertices.set(i - 1, tempNode);
					i = i / 2;
				}//end inner else
			}//end while
		}
	}
	
	//Method to find the minBandwidth
	public int findMinBandwidth(String a, String b) {
		// if a and b are inside the heap, run modified Dijkstra's algorithm from a to b
		if (vId.containsKey(a) && vId.containsKey(b)) {
			//give the source vertex the infinite priority
			updatePriority(findIndex(a), Integer.MAX_VALUE);
			
			Vertex temp = vertices.get(findIndex(a));
			vertices.set(findIndex(a), vertices.get(0));
			vertices.set(0, temp);
			
			//update the priority for every vertex who is adjacent to source vertex
			for (Map.Entry<String, Integer> e: temp.adjVertex.entrySet()) {
				if (vertices.get(findIndex(e.getKey())).priority < e.getValue()) {
					updatePriority(findIndex(e.getKey()), e.getValue());
				}
			}
			
			//modified Dijkstra's algorithm  
			while (vertices.size() > 0) {
				Vertex u = remove(0);
				removed.add(u);
				
				if (minBandwidth > u.priority) {
					minBandwidth = u.priority;
				}
				
				if (u.name.equals(b)) {
					return minBandwidth;
				}
				
				for (Map.Entry<String, Integer> e: u.adjVertex.entrySet()) {
					boolean found = false;
					for (int i = 0; i < removed.size(); i++) {
						if (removed.get(i).name.equals(e.getKey())) {
							found = true;
							break;
						}
					}
					if (found) {
						continue;
					}
					int index = findIndex(e.getKey());
					updatePriority(index, Math.max(vertices.get(findIndex(e.getKey())).priority, e.getValue()));
				}
			}	
		}
		else if (!vId.containsKey(a)) {
			System.out.printf("Vertex %s hasn't been added.\n", a);
			System.exit(1);
		}
		else if (!vId.containsKey(b)) {
			System.out.printf("Vertex %s hasn't been added.\n", b);
			System.exit(1);
		}
		
		System.out.println("findMST before return is OK");
		return minBandwidth;
	}
}
