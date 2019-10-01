package UniqueLockingMethod;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UniqueLock {

	private static Map lockDataMap = new ConcurrentHashMap<String, LockData>();

	public static String SERVICENAME1 = "SERVICENAME1";
	public static String SERVICENAME2 = "SERVICENAME2";
	public static String SERVICENAME3 = "SERVICENAME3";
	public static String SERVICENAME4 = "SERVICENAME4";

	public class LockData {

		// Creates a random access file stream to read from, and optionally to
		// write to
		private FileChannel channel = null;

		// Acquire an exclusive lock on this channel's file (blocks until lock
		// can be retrieved)
		private FileLock lock = null;

		public LockData(FileChannel channel, FileLock lock) {

			super();
			this.channel = channel;
			this.lock = lock;
		}

		public FileChannel getChannel() {
			return channel;
		}

		public FileLock getLock() {
			return lock;
		}
	}

	public static boolean getLock(String serviceName) {

		boolean bRet = false;
		try {

			File file = new File(serviceName + ".dat");
			// Creates a random access file stream to read from, and optionally
			// to write to
			FileChannel channel = new RandomAccessFile(file, "rw").getChannel();
			FileLock lock = null;
			// Attempts to acquire an exclusive lock on this channel's
			// file (returns null or throws an exception if the file
			// is already locked.
			try {

				lock = channel.tryLock();
				bRet = true;
				UniqueLock.LockData storeFileLockData = new UniqueLock().new LockData(channel, lock);
				lockDataMap.put(serviceName, storeFileLockData);
			} catch (OverlappingFileLockException e) {
				bRet = false;
			}
		} catch (IOException e) {
			bRet = false;
		}
		return bRet;
	}

	public static boolean releaseLock(String serviceName) {

		boolean bRet = false;
		try {
			UniqueLock.LockData storeFileLockData = (UniqueLock.LockData) lockDataMap.get(serviceName);			
			if(null != storeFileLockData){
				// release the lock
				storeFileLockData.getLock().release();
				// close the channel
				storeFileLockData.getChannel().close();
				lockDataMap.remove(serviceName);
				bRet = true;
			}
		} catch (IOException e) {
			bRet = false;
		}
		return bRet;
	}
}
