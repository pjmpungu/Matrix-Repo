package Matrix;

public class Matrix {
	
	//source code found at https://gist.github.com/hallazzang/4e6abbb05ff2d3e168a87cf10691c4fb 
	
    private double[][] data = null;
    private int rows = 0, cols = 0;

    public Matrix(int rows, int cols) {
        data = new double[rows][cols];
        this.rows = rows;
        this.cols = cols;
    }

    public Matrix(double[][] data) {
        this.data = data.clone();
        rows = this.data.length;
        cols = this.data[0].length;
    }

    public boolean isSquare() {
        return rows == cols;
    }

    public void display() {
        System.out.print("[");
        for (int row = 0; row < rows; ++row) {
            if (row != 0) {
                System.out.print(" ");
            }

            System.out.print("[");

            for (int col = 0; col < cols; ++col) {
                System.out.printf("%8.3f", data[row][col]);

                if (col != cols - 1) {
                    System.out.print(" ");
                }
            }

            System.out.print("]");

            if (row == rows - 1) {
                System.out.print("]");
            }

            System.out.println();
        }
    }

    public Matrix transpose() {
        Matrix result = new Matrix(cols, rows);

        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                result.data[col][row] = data[row][col];
            }
        }

        return result;
    }

    // Note: exclude_row and exclude_col starts from 1
    public static Matrix subMatrix(Matrix matrix, int exclude_row, int exclude_col) {
        Matrix result = new Matrix(matrix.rows - 1, matrix.cols - 1);

        for (int row = 0, p = 0; row < matrix.rows; ++row) {
            if (row != exclude_row - 1) {
                for (int col = 0, q = 0; col < matrix.cols; ++col) {
                    if (col != exclude_col - 1) {
                        result.data[p][q] = matrix.data[row][col];

                        ++q;
                    }
                }

                ++p;
            }
        }

        return result;
    }

    public double determinant() {
        if (rows != cols) {
            return Double.NaN;
        }
        else {
            return _determinant(this);
        }
    }

    private double _determinant(Matrix matrix) {
        if (matrix.cols == 1) {
            return matrix.data[0][0];
        }
        else if (matrix.cols == 2) {
            return (matrix.data[0][0] * matrix.data[1][1] -
                    matrix.data[0][1] * matrix.data[1][0]);
        }
        else {
            double result = 0.0;

            for (int col = 0; col < matrix.cols; ++col) {
                Matrix sub = subMatrix(matrix, 1, col + 1);

                result += (Math.pow(-1, 1 + col + 1) *
                           matrix.data[0][col] * _determinant(sub));
            }

            return result;
        }
    }

    public Matrix inverse() {
        double det = determinant();

        if (rows != cols || det == 0.0) {
            return null;
        }
        else {
            Matrix result = new Matrix(rows, cols);

            for (int row = 0; row < rows; ++row) {
                for (int col = 0; col < cols; ++col) {
                    Matrix sub = subMatrix(this, row + 1, col + 1);

                    result.data[col][row] = (1.0 / det *
                                             Math.pow(-1, row + col) *
                                             _determinant(sub));
                }
            }

            return result;
        }
    }
    
    //all of the code above is not mine, and taken from the github link listed at the top of the class
    //all of the code below is mine alone
    
    //method to create an nxn identity matrix
    
    public static Matrix identityMatrix(int n) {
    	
    	double[][] I = new double[n][n];
    	
    	int index = 0;
    	boolean oneFilled = false;
    	
    	//loop through the array filling up the spaces with 0s
    	//if we haven't added a 1 to the current row and we're at the right index, 
    	//then fill up the space with a 1
    	
    	for (int row = 0; row < n; row++) {
    		
    		for(int col = 0; col < n; col ++) {
    			
    			if(col == index && !oneFilled) {
    				
    				I[row][col] = 1;
    				index++;
    				oneFilled = true;
    				
    			}else {
    				
    				I[row][col] = 0;
    				
    			}
    			
    		}
    		
    		oneFilled = false;
    		
    	}
    	
    	return new Matrix(I);
    }

  //getter methods for columns and rows
    
	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}
	
	public double[][] getData() {
		return data;
	}
	
	//method to subtract matrices from each other
	

	public static Matrix minus(Matrix A, Matrix B) {
		
		
		//first validate the data to ensure the number of rows equals the number of columns
		
		if(A.getRows() == B.getRows() && A.getCols() == B.getCols()) {
			
			Matrix C = new Matrix(A.getRows(), A.getCols());
			
			//loop through each value of the array to find the difference
			
			for(int r = 0; r < A.getRows(); r++) {
				
				for (int c = 0; c < A.getCols(); c++) {
					
					C.getData()[r][c] = A.getData()[r][c] - B.getData()[r][c];
					
				} 
				
			}
			
			return C;
			
		}else {
			
			//if the matrices are different sizes, display an error
			
			System.out.println("Error, matrices are different sizes");
			return null;
			
		}
		
	}
	
	
	//method for matrix multiplication
	
	public static Matrix times(Matrix A, Matrix B) {
		
		//first verify the number of columns in Matrix A equals the number
		//of rows in Matrix B
		
		if(A.getCols() == B.getRows()) {
			
			double[][] vals = new double[A.getRows()][B.getCols()];
			
			for(int r = 0; r < A.getRows(); r++) {
				
				for(int c = 0; c < B.getCols(); c++) {
					
					double value = 0;
					
					for(int index = 0; index < A.getCols(); index++) {
						
						value += A.getData()[r][index] * B.getData()[index][c];
						
					}
					
					vals[r][c] = value;
					
				}
				
			}
			
			return new Matrix(vals);
			
		}else {
			
			//if the num of columns in A != rows in B, return an empty matrix
			System.out.println("Error, invalid matrix multiplication");
			return new Matrix(new double[1][1]);
			
		}
		
	}
    
    
    
}