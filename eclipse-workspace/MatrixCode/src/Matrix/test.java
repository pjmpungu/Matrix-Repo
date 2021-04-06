package Matrix;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		double[][] vals = {{0, 5.0/20},{10.0/20, 0}};
	    Matrix A = new Matrix(vals);
	    double[][] vals2 = {{3}, {2}};
	    Matrix D = new Matrix(vals2);
	    
	    Matrix I = Matrix.identityMatrix(2);
	    Matrix.minus(I, A).display();
	    Matrix inverse = Matrix.minus(I, A).inverse();
	    
	    Matrix C = Matrix.times(inverse, D);
	    
	    C.display();
	}

}
