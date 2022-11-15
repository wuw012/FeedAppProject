#!/usr/bin/env python
import pika, sys, os
import pymongo
import json

def main():
    connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
    channel1 = connection.channel()
    channel2 = connection.channel()

    channel1.queue_declare(queue='topic.queue1')
    channel2.queue_declare(queue='topic.queue2')

    def insertToMongodb(pollJson):
        myclient = pymongo.MongoClient("mongodb://localhost:27017/")
        mydb = myclient["PollDatabase"]
        mycol = mydb["expiredPolls"]

        mycol.insert_one(pollJson)

    def callback(ch, method, properties, body):
        #bodyJson = json.loads(body.decode())
        #insertToMongodb(bodyJson)
        print(" [x] Poll created: %r" % body.decode())

    def callback2(ch, method, properties, body):
        bodyJson = json.loads(body.decode())
        insertToMongodb(bodyJson)
        print(" [x] Poll expired: %r" % body.decode())


    channel1.basic_consume(queue='topic.queue1', on_message_callback=callback, auto_ack=True)
    channel2.basic_consume(queue='topic.queue2', on_message_callback=callback2, auto_ack=True)

    print(' [*] Waiting for messages. To exit press CTRL+C')
    channel1.start_consuming()
    channel2.start_consuming()

if __name__ == '__main__':
    try:
        main()
    except KeyboardInterrupt:
        print('Interrupted')
        try:
            sys.exit(0)
        except SystemExit:
            os._exit(0)