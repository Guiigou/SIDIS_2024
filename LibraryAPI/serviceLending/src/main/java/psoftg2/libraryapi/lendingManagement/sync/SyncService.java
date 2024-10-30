package psoftg2.libraryapi.lendingManagement.sync;

import org.springframework.stereotype.Service;

@Service
public class SyncService {

    public void syncData(SyncRequest syncRequest) {
        if ("create".equals(syncRequest.getAction())) {
            System.out.println("Sincronizando novo leitor com ID: " + syncRequest.getReaderId());
        } else if ("update".equals(syncRequest.getAction())) {
            System.out.println("Sincronizando atualização do leitor com ID: " + syncRequest.getReaderId());
        } else if ("delete".equals(syncRequest.getAction())) {
            System.out.println("Sincronizando remoção do leitor com ID: " + syncRequest.getReaderId());
        }
    }
}

