package homework2;

/**
 * A WeightedNode class is a record type which contains a name, a cost, a color (and a XXX if needed)
 **/
public class WeightedNode implements Comparable<WeightedNode> {
	
	/**
	 * Name of this node.
	 */
  	private final String name;
  	
  	
  	/**
  	 * Cost of this node.
  	 */
  	private final int cost;
  	
  	private int backEdges;

	
  	/**
  	 * Color of this node.
  	 */	
	private String color;
	
	
	
  	/**
     * Creates a WeightedNode.
     * @effects creates new WeightedNode with the name
     * <tt>name</tt> and the cost <tt>cost</tt> and a default color White.
     *
     */
	 
  	public WeightedNode(String name, int cost) {
    	this.name = name;
      	this.cost = cost;
		this.color = "White";
		this.backEdges = 0;
  	}

  
	/**
	 * Returns this.name.
     * @return this.name
     */
  	public String getName() {
    	return name;
  	}


	/**
	 * Returns this.cost.
     * @return this.cost
     */
  	public int getCost() {
    	return cost;
  	}

	/**
	 * Returns this.color.
     * @return this.color
     */	
	public String getColor(){
		return color;
	}
	
	/**
	 * @requires valid string color
	 * @modifies this
	 * @effects the color of the object
     */	
	public void setColor(String color){
		this.color = color;
	}
	
	/**
     * Returns this.backEdges.
     * @return this.backEdges
     */ 
	// <Itay> Modified
	public int getBackEdges() {
	    return this.backEdges;
	}
	
	/**
     * @requires valid back edges number, zero or greater
     * @modifies this
     * @effects the back edges number of the object
     */ 
	// <Itay> Modified
	public void setBackEdges(int backEdges) {
	    this.backEdges = backEdges;
	}
	
	/**
	 * Standard equality operation.
	 * @return true iff o.instaceOf(WeightedNode) &&
	 *         (this.name.eqauls(o.name) && (this.cost == o.cost))
	 */
	public boolean equals(Object o) {
    	if (o instanceof WeightedNode) {
      		WeightedNode other = (WeightedNode)o;
      		return this.name.equals(other.name) &&
				   (this.cost == other.cost);
    	}
    	return false;
  	}
  
  
	/**
	 * Returns a hash code value for this.
	 * @return a hash code value for this.
	 */
  	public int hashCode() {
    	return name.hashCode();
  	}


	/**
	 * Standard object to string conversion.
	 * @return a string representation of this in the form [name: cost].
	 */
  	public String toString() {
    	return "[" + name + ": " + cost + "]";
  	}


	/**
	 * Compares this with the specified object for order.
	 * @return a negative integer, zero, or a positive integer as this
	 * 		   object is respectively less than, equal to, or greater than
	 *         the specified object .
	 * 		   <p>
     * 		   WeightedNodes are ordered by their numeric ordering of their costs.
     * 		   When two nodes share a cost, their ordering is determined 
     *         lexicographically by their nameic ordering.
     */
  	public int compareTo(WeightedNode o) {	
    	int diff_cost = cost - o.cost;
      	if (diff_cost == 0)
			return name.compareTo(o.name);
      	else
			return diff_cost;
  	}

}

