package br.com.produban.agencia.ligacoes.reader;

	import java.nio.file.FileSystems;
	import java.nio.file.Path;
	import java.nio.file.StandardWatchEventKinds;
	import java.nio.file.WatchEvent;
	import java.nio.file.WatchEvent.Kind;
	import java.nio.file.WatchKey;
	import java.nio.file.WatchService;
	 
	public class JavaDirectoryChangeListener implements Runnable {
		
		private static Path directoryPath;
	 
		public static void main(String[] args) {
			
			directoryPath = FileSystems.getDefault().getPath("/home/dayane/projetos/ligacoes");
			Thread thread = new Thread(new JavaDirectoryChangeListener());
	        thread.start();
	 
		}
	 
		public void run() {
			try {
	            WatchService watchService = directoryPath.getFileSystem().newWatchService();
	            directoryPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
	                    StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
	 
	           
	            while (true) {
	                
	            	WatchKey watchKey = watchService.take();
	 
	                for (final WatchEvent<?> event : watchKey.pollEvents()) {
	                    takeActionOnChangeEvent(event);
	                }
	 
	                if (!watchKey.reset()) {
	                    watchKey.cancel();
	                    watchService.close();
	                    System.out.println("Watch directory got deleted. Stop watching it.");
	                    break;
	                }
	            }
	 
	        } catch (InterruptedException interruptedException) {
	            System.out.println("Thread got interrupted:"+interruptedException);
	            return;
	        } catch (Exception exception) {
	        	exception.printStackTrace();
	            return;
	        }
			
		}
		
		private void takeActionOnChangeEvent(WatchEvent<?> event) {
	        
			Kind<?> kind = event.kind();
	        
			if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {
	            Path entryCreated = (Path) event.context();
	            System.out.println("New entry created:" + entryCreated);
	        } else if (kind.equals(StandardWatchEventKinds.ENTRY_DELETE)) {
	            Path entryDeleted = (Path) event.context();
	            System.out.println("Exissting entry deleted:" + entryDeleted);
	        } else if (kind.equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
	            Path entryModified = (Path) event.context();
	            System.out.println("Existing entry modified:"+ entryModified);
	        }
	    }
	 
	}
