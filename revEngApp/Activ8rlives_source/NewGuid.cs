// System.Guid
using System.Security.Cryptography;

public static Guid NewGuid()
{
	byte[] array = new byte[16];
	lock (_rngAccess)
	{
		if (_rng == null)
		{
			_rng = RandomNumberGenerator.Create();
		}
		_rng.GetBytes(array);
	}
	Guid guid = new Guid(array);
	guid._d = (byte)((guid._d & 0x3F) | 0x80);
	guid._c = (short)(((long)guid._c & 4095L) | 0x4000);
	return guid;
}
