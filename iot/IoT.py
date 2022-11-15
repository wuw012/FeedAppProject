import tkinter as tk
from tkinter import *
from tkinter import ttk
import requests
import json
import base64

class feedAppService:
    CREATE_USER_URL = "http://localhost:8080/agents/createUser"
    API_URL = 'http://localhost:8080/polls'
    payload = {
        "Username": "iot_001",
        "Password": "1664"
    }
    headers = {
        'Content-Type': 'application/json',
        'Authorization': '',
    }

    #create device object json
    deviceObject = {
    "username":"iot_001",
    "email":"device@devices.com",
    "password": "1664",
    "role":"DEVICE"
    }

    votes = []

    currentPoll = {
        "pollID": 0,
        "question": "No Poll selected"
    }

    def setHeaders():
        authString = f"{feedAppService.payload['Username']}:{feedAppService.payload['Password']}"
        authBytes = authString.encode("utf-8")
        b64 = base64.b64encode(authBytes)
        feedAppService.headers["Authorization"]=f"Basic {b64.decode()}"
        print(feedAppService.headers)

    def createDevice():
        feedAppService.setHeaders()
        response = {}
        try:
            response = requests.post(feedAppService.CREATE_USER_URL, headers=feedAppService.headers, data=json.dumps(feedAppService.deviceObject))
        except Exception as e:
            print("Could not create device, exception occured: ",e)

        if not (response.status_code == 409):
            feedAppService.deviceJson = response.json()
            print("Account created:",feedAppService.deviceJson)
            feedAppService.voter_id = (response.json()['agentID'])
            return feedAppService.voter_id
        else:
            feedAppService.deviceJson = requests.get("http://localhost:8080/agents/byUsername/"+feedAppService.deviceObject["username"], headers=feedAppService.headers).json()
            print("Account retrieved: ", feedAppService.deviceJson)
            feedAppService.voter_id = feedAppService.deviceJson["agentID"]
        print("Could not create device account, failed with status code",response.status_code,". Error:",response.reason)

    def createBatchVote():
        batchVote = {
        "voter_id": feedAppService.voter_id,
        "votes" : feedAppService.votes
        }
        return batchVote
    
    def getPoll(poll_id):
        response = requests.get(feedAppService.API_URL+ "/" + str(poll_id), headers=feedAppService.headers, data=feedAppService.payload)
        feedAppService.pollID=poll_id

        if response.status_code == 500:
            errorHandler("No poll with ID "+poll_id)

        if response.status_code == 401:
            errorHandler("Wasn't authorized to get poll"+poll_id)

        if response.json()['status'] == "ACTIVE":
            feedAppService.currentPoll['pollID'] = response.json()['pollID']
            feedAppService.currentPoll['question'] = response.json()['question']
        else:
            errorHandler("Poll with id: " + str(poll_id) + " is not active")
            feedAppService.currentPoll['pollID'] = poll_id
            feedAppService.currentPoll['question'] = "Inactive Poll"
        print(response.json())
        return response.json()
    
    def postBatchVotes():
        response = requests.post(feedAppService.API_URL+ "/" + str(feedAppService.pollID)+"/batchVote", headers=feedAppService.headers, data=json.dumps(feedAppService.createBatchVote()))
        feedAppService.votes = []
        print(response.json())

window = Tk()
window.title("FeedApp")
feedAppService.voter_id = feedAppService.createDevice()


# Create a label
pollid_view = ttk.Label(window, text="Poll ID: " + (str(feedAppService.currentPoll['pollID'])))
poll_question_view = ttk.Label(window, text="Question: " + (feedAppService.currentPoll['question']))

device_votes_view = ttk.Label(window, text="Votes:" + str(feedAppService.votes))

def update_text():
    pollid_view['text'] = "Poll ID: " + str((feedAppService.currentPoll['pollID']))
    poll_question_view['text'] = "Question: " + (feedAppService.currentPoll['question'])
    device_votes_view['text'] = "Votes: " + str(feedAppService.votes)
    window.after(1000, update_text)


def setCurrentPoll(poll_id):
    feedAppService.currentPoll = feedAppService.getPoll(poll_id)
    feedAppService.votes = []
    update_text()

update_text()

device_errors = ttk.Label(window, text="")

def errorHandler(error):
    device_errors['text'] = error
    # make the error message disappear after 5 seconds
    window.after(5000, lambda: errorHandler(""))

exit_button = ttk.Button(
    window,
    text='Exit',
    command=lambda: window.quit()
)

submit_button = ttk.Button(
    window,
    text='Submit',
    command=lambda:(
        setCurrentPoll(inputPollId.get()),
         feedAppService.getPoll(inputPollId.get())))

send_button = ttk.Button(
    window,
    text='Send',
    command=lambda:(
        feedAppService.createBatchVote(),
         feedAppService.postBatchVotes(),
          errorHandler("Votes sent"))
)

vote_yes_button = ttk.Button(
    window,
    text='Yes',
    command=lambda:(
        feedAppService.votes.append(True),
         print(feedAppService.votes))
)

vote_no_button = ttk.Button(
    window,
    text='No',
    command=lambda:(
        feedAppService.votes.append(False), 
        print(feedAppService.votes))
)

inputPollId = ttk.Entry(
    window,
    width=30
)

inputPollId.pack()
submit_button.pack()

pollid_view.pack()
poll_question_view.pack()
device_votes_view.pack()


vote_yes_button.pack(
    ipadx=5,
    ipady=2,
    expand=True
)
vote_no_button.pack(
    ipadx=5,
    ipady=5,
    expand=True
)
send_button.pack(
    ipadx=5,
    ipady=5,
    expand=True
)

device_errors.pack()

exit_button.pack(
    ipadx=5,
    ipady=5,
    expand=True
)

canvas = Canvas(height=0, width=300, bg='white')
canvas.pack()

window.mainloop()