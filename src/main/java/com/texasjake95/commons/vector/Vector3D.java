package com.texasjake95.commons.vector;

public class Vector3D extends Vector2D {
	
	private static final long serialVersionUID = 1332327618219891560L;
	protected float z;
	
	public Vector3D()
	{
		super();
	}
	
	public Vector3D(float x)
	{
		super(x);
	}
	
	public Vector3D(float x, float y)
	{
		super(x, y);
	}
	
	public Vector3D(float x, float y, float z)
	{
		super(x, y);
		this.z = z;
	}
	
	public Vector3D(Vector2D vec, float z)
	{
		this(vec.x, vec.y, z);
	}
	
	public Vector3D addition(Vector3D vec)
	{
		Vector2D vec2D = super.addition(vec);
		float VecZ = this.z + vec.z;
		return new Vector3D(vec2D, VecZ);
	}
	
	public Vector3D addition(Vector3D... vecs)
	{
		Vector3D current = new Vector3D();
		for (Vector3D vec : vecs)
			current = current.addition(vec);
		return current;
	}
	
	public Vector3D subtaction(Vector3D vec)
	{
		Vector2D vec2D = super.subtaction(vec);
		float VecZ = this.z - vec.z;
		return new Vector3D(vec2D, VecZ);
	}
	
	public Vector3D subtaction(Vector3D... vecs)
	{
		Vector3D current = new Vector3D();
		for (Vector3D vec : vecs)
			current = current.subtaction(vec);
		return current;
	}
	
	public float dotProduct(Vector3D vec)
	{
		float VecXY = super.dotProduct(vec);
		float VecZ = vec.z * this.z;
		return VecXY + VecZ;
	}
	
	public float dotProduct(Vector3D... vecs)
	{
		float VecXY = super.dotProduct(vecs);
		float VecZ = this.z;
		for (Vector3D vec : vecs)
		{
			VecZ *= vec.z;
		}
		return VecXY + VecZ;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + " " + z + ":k";
	}
	
	@Override
	public float getMagnitude()
	{
		float XYsq = super.getMagnitude();
		float Zsq = z * z;
		return (float) Math.sqrt(XYsq * XYsq + Zsq);
	}
	
	public Vector3D cross(Vector3D vec)
	{
		float x = this.y * vec.z - this.z * vec.y;
		float y = this.x * this.z - this.z * vec.x;
		float z = this.x * this.y - this.y * this.x;
		return new Vector3D(x, y, z);
	}
}
