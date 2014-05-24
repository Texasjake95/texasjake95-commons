package com.texasjake95.commons.vector;

import com.texasjake95.commons.helpers.MathHelper;

public class Vector3D extends Vector2D {
	
	private static final long serialVersionUID = 1332327618219891560L;
	protected double z;
	
	public Vector3D()
	{
		super();
		this.z = 0;
	}
	
	public Vector3D(double x)
	{
		super(x);
	}
	
	public Vector3D(double x, double y)
	{
		super(x, y);
	}
	
	public Vector3D(double x, double y, double z)
	{
		super(x, y);
		this.z = z;
	}
	
	public Vector3D(Vector2D vec, double z)
	{
		this(vec.x, vec.y, z);
	}
	
	public Vector3D addition(Vector3D vec)
	{
		Vector2D vec2D = super.addition(vec);
		double VecZ = this.z + vec.z;
		return new Vector3D(vec2D, VecZ);
	}
	
	public Vector3D addition(Vector3D... vecs)
	{
		Vector3D current = this;
		for (Vector3D vec : vecs)
			current = current.addition(vec);
		return current;
	}
	
	public Vector3D subtaction(Vector3D vec)
	{
		Vector2D vec2D = super.subtaction(vec);
		double VecZ = this.z - vec.z;
		return new Vector3D(vec2D, VecZ);
	}
	
	public Vector3D subtaction(Vector3D... vecs)
	{
		Vector3D current = this;
		for (Vector3D vec : vecs)
			current = current.subtaction(vec);
		return current;
	}
	
	public double dotProduct(Vector3D vec)
	{
		double VecXY = super.dotProduct(vec);
		double VecZ = vec.z * this.z;
		return VecXY + VecZ;
	}
	
	public double dotProduct(Vector3D... vecs)
	{
		double VecXY = super.dotProduct(vecs);
		double VecZ = this.z;
		for (Vector3D vec : vecs)
		{
			VecZ *= vec.z;
		}
		return VecXY + VecZ;
	}
	
	@Override
	public Vector3D multiply(double scalar)
	{
		return new Vector3D(super.multiply(scalar), this.z * scalar);
	}
	
	@Override
	public String toString()
	{
		return this.constructString() + " Magnitude: " + this.getMagnitude();
	}
	
	@Override
	protected String constructString()
	{
		return super.constructString() + " " + z + ":k";
	}
	
	@Override
	public double getMagnitude()
	{
		double XYsq = super.getMagnitude();
		double Zsq = z * z;
		return (double) Math.sqrt(XYsq * XYsq + Zsq);
	}
	
	public double getAngleMadeWithVector(Vector3D vec)
	{
		double vecMag = vec.getMagnitude();
		double dot = this.dotProduct(vec);
		double thisMag = this.getMagnitude();
		double ratio = dot / (thisMag * vecMag);
		if (ratio > 1)
			ratio = 1;
		if (ratio < -1)
			ratio = -1;
		ratio = MathHelper.round(ratio, 4);
		double angle = (double) Math.acos(ratio);
		angle = MathHelper.round(angle, 4);
		return angle;
	}
	
	public Vector3D cross(Vector3D vec)
	{
		double x = this.y * vec.z - this.z * vec.y;
		double y = this.z * vec.x - this.x * vec.z;
		double z = this.x * vec.y - this.y * vec.x;
		return new Vector3D(x, y, z);
	}
	
	public Vector3D getUnitVector()
	{
		double magnitude = this.getMagnitude();
		return this.multiply(1 / magnitude);
	}
}
