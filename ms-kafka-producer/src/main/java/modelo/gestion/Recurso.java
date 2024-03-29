package modelo.gestion;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cfg.KafkaConfig;
import modelo.entidades.Usuario;

@Service
@RestController
@Scope("request")
public class Recurso {

	private String TOPIC = "DOOM";	
	private static KafkaConfig kc = new KafkaConfig();


	// Se crea un logger para la clase
	Logger log = LoggerFactory.getLogger(Recurso.class);

	@PostMapping(path="/enviar")
	@CrossOrigin("http://localhost:4200")
	public void mandarUsuario (@RequestBody Usuario u) throws InterruptedException, ExecutionException {
		// Se usa para que la misma key siempre vaya a la misma particion

		final Producer<String, Usuario> producer = kc.createProducer();

		ProducerRecord<String, Usuario> record = new ProducerRecord<String, Usuario>(TOPIC, u);

		// Operacion asincrona, a�adimos un callback para recibir feedback del mensaje
		producer.send(record, new Callback() {

			@Override
			public void onCompletion(RecordMetadata metadata, Exception e) {
				// Se ejecuta siempre que se manda o se genera una excepcion
				if (e == null) {
					log.info("\nMetadatos recibidos. Topic: " + metadata.topic() + " particion: "
							+ metadata.partition() + " offset: " + metadata.offset() 
							+ " key: " + record.key() + " time: " + metadata.timestamp());
				}

				else {
					log.error("Error", e);
				}
			}
		});

		System.out.printf("sent record(key=%s value=(%s)) ", record.key(), record.value());

		// Se necesita para forzar el envio
		producer.flush();
		// Flush and close
		producer.close();
	}
}
