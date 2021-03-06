#!/usr/bin/env python
import pika
import sys

connection = pika.BlockingConnection(pika.ConnectionParameters(
    host='localhost', port=5672, virtual_host="/logbacklog",
    credentials=pika.PlainCredentials('logbacklog', 'logbacklog')))
channel = connection.channel()

# channel.exchange_declare(exchange='logbackdemo',
#                         type='topic')

# result = channel.queue_declare(exclusive=True)
# queue_name = result.method.queue

# binding_keys = sys.argv[1:]
# if not binding_keys:
#     sys.stderr.write("Usage: %s [binding_key]...\n" % sys.argv[0])
#     sys.exit(1)

# for binding_key in binding_keys:
#     channel.queue_bind(exchange='logbackdemo',
#                        queue="",
#                        routing_key=binding_key)

print(' [*] Waiting for logs. To exit press CTRL+C')


def callback(ch, method, properties, body):
    # print(" [x] %r:%r" % (method.routing_key, body))
    print("%r" % body)


channel.basic_consume(callback,
                      queue="logbackdemo",
                      no_ack=True)

channel.start_consuming()
