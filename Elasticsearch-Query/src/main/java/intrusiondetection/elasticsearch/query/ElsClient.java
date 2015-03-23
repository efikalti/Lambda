package intrusiondetection.elasticsearch.query;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 *
 * @author efi
 */
public class ElsClient {

    Node node;
    Client client;
    Settings settings;
    TransportClient tclient;
    
    public ElsClient()
    {
        String cluster = Config.getInstance().getProperty("elasticsearch_cluster");
        node = nodeBuilder().clusterName(cluster).client(false).node();
        client = node.client();
        settings = ImmutableSettings.settingsBuilder().put("client.transport.sniff", true).build();
        tclient = new TransportClient(settings);
        this.print();
    }
    
    public void close()
    {
        node.close();
    }
    
    public void print()
    {
        System.out.println(""+ this.node.toString());
        System.out.println("" + this.client.toString());
        System.out.println("" + this.tclient.toString());
    }
}
