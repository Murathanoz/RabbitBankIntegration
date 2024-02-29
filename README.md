
# RabbitMQ

RabbitMQ is a message queue (message queue) system. It is a scalable and powerful system that stores asynchronous (asynchronous) operations performed through our program and proceeds by sequentially pulling them from the queue and executing them.
 
## 1.RabbitMQ and Components
 - Producer: It is the application that sends and produces messages to the queue.
 - Consumer: It is an application that processes the messages in the queue it listens to.
 - Routing Key: It is the notification by marking which queue the message produced by the Producer and transmitted to Exchange will be directed to.
 - Binding: It is the routing rule for the connection to be established between Exchange and Queue. Exchange distributes the messages it receives to the relevant queues according to this rule.
 - Queue: It is the list where messages are added, consumed and kept.
 - Exchange: The producer does not send the message it produces directly to the receiver or queue; there is a message router in the middle, which is the structure that performs the routing process. The producer transmits the message it produces to the exchange, the exchange adds the incoming message to the queue with relevant information, and if there is a consumer listening, it takes the next message from the queue to process. In short, its task is to forward the message it receives to the relevant Queue according to the specified Routing Key.
 
## Exchange Types
It is the type that specifies the method by which the message will be delivered to the queue. 
It allows us to send messages under certain standards. In other words, it allows us to receive data and messages according to certain standards. There are three types of exchanges:
- Direct Exchange: Routing Key is determined and this information is written to the relevant queue. Consumer acts according to this key information.
- Fanout Exchange: All messages in Exchange are sent to all queues. Those with Routing Key are ignored.
- Topic Exchange: Topic Exchange writes to different queues according to the given key. It also has its own WildCard support. For example, it is possible to access a specific group or all messages with “*” and “#”.

## 2.Implementation for Java
First run this comment for using Docker 

```bash
docker run -d --hostname my-rabbit --name some-rabbit -p 15672:15672 rabbitmq:3-management
```

* Definitin in application.properties

```bash
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
spring.rabbitmq.exchange=default
rb.exchange.name=CodingInTime-Exchange
sr.rabbit.first.queue.name = firstStepQueue
sr.rabbit.second.queue.name = secondStepQueue
sr.rabbit.third.queue.name = thirdStepQueue
```

define this in RabbitProperty.class


```bash
      @Value("${spring.rabbitmq.exchange}")
	   public String exchange;
	   
	   @Value("${sr.rabbit.first.queue.name}")
	   private String firstStepQueue;
	   
	   @Value("${sr.rabbit.second.queue.name}")
	   private String secondStepQueue;
	   
	   @Value("${sr.rabbit.third.queue.name}")
	   private String thirdStepQueue;
	   
	   @Value("${sr.rabbit.first.routing.name}")
	   private String firstRoute;
	   
	   @Value("${sr.rabbit.second.routing.name}")
	   private String secondRoute;
	   
	   @Value("${sr.rabbit.third.routing.name}")
	   private String thirdRoute;
```

in RabbitMqCongig file we have defined exchange type and queue for binding


```bash
    @Autowired
	private RabbitProperty rabbitPerperty;
   
    @Bean
    DirectExchange exchange() {
        return new DirectExchange(rabbitPerperty.getExchange());
    }

    @Bean
    Queue firstStepQueue(){
        return new Queue(rabbitPerperty.getFirstStepQueue(), false);
    }

    @Bean
    Queue secondStepQueue() {
        return new Queue(rabbitPerperty.getSecondStepQueue(), true);
    }

    @Bean
    Queue thirdStepQueue() {
        return new Queue(rabbitPerperty.getThirdStepQueue(), true);
    }

    @Bean
    Binding binding(Queue firstStepQueue, DirectExchange exchange){
        return BindingBuilder.bind(firstStepQueue).to(exchange).with(rabbitPerperty.getFirstRoute());
    }

    @Bean
    Binding secondBinding(Queue secondStepQueue, DirectExchange exchange){
        return BindingBuilder.bind(secondStepQueue).to(exchange).with(rabbitPerperty.getSecondRoute());
    }

    @Bean
    Binding thirdBinding(Queue thirdStepQueue, DirectExchange exchange){
        return BindingBuilder.bind(thirdStepQueue).to(exchange).with(rabbitPerperty.getThirdRoute());
    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
```
after that we can create listener


```bash
    @RabbitListener(queues = "${sr.rabbit.first.queue.name}")
     public void transferMoneyMessageHandler(MoneyTransferRequest transferRequest) {
        userService.transferMoneyMessage(transferRequest);
     }
	
	 @RabbitListener(queues = "${sr.rabbit.second.queue.name}")
	    public void updateReceiverAccountHandler(MoneyTransferRequest transferRequest) {
		 userService.updateReceiverAccount(transferRequest);
	 }
	 
	 @RabbitListener(queues = "${sr.rabbit.third.queue.name}")
	    public void finalizeTransferHandler(MoneyTransferRequest transferRequest) {
		 userService.finalizeTransfer(transferRequest);
	 }
```

now we can send data to RabbitMq

```bash
    @Autowired
	private RabbitProperty rabbitPerperty;
	@Autowired
	private  DirectExchange exchange;
	@Autowired
    private  AmqpTemplate rabbitTemplate;
    
	public void transferMoneyProduce(MoneyTransferRequest transferRequest){
        rabbitTemplate.convertAndSend(exchange.getName(), rabbitPerperty.getFirstRoute(), transferRequest);
    }
 
	public void sendReceiverProduce(MoneyTransferRequest transferRequest) {
        rabbitTemplate.convertAndSend(exchange.getName(), rabbitPerperty.getSecondRoute(), transferRequest);
	}
	
	public void finalizeTransferProduce(MoneyTransferRequest transferRequest) {
        rabbitTemplate.convertAndSend(exchange.getName(), rabbitPerperty.getThirdRoute(), transferRequest);
	}
```
## Request

- PUT request /v1/transfer 

```bash
{
    "fromId":"101",
    "toId":"102",
    "amount":30
}
```
## Requirements
- Java 17
- Jpa
- H2 Database
- RabbitMq

 
## Stay in touch

- Author - [Murat Öz](https://www.linkedin.com/in/murat-%C3%B6z-781a45135?originalSubdomain=tr)



