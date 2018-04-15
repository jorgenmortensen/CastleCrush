package com.castlecrush.game;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;
import googleplayservice.PlayServices;
import googleplayservice.PlayerData;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMultiplayer;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.example.games.basegameutils.GameHelper;

import static com.google.android.gms.games.GamesActivityResultCodes.RESULT_LEFT_ROOM;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class AndroidLauncher extends AndroidApplication implements PlayServices, RoomUpdateListener, RoomStatusUpdateListener, OnInvitationReceivedListener, RealTimeMessageReceivedListener{

	private static final String TAG = "CastleCrush" ;
	private GameHelper gameHelper;

	public static final int OK =0;
	private static final int RC_SELECT_PLAYERS = 10000;
	private static final int RC_INVITATION_INBOX = 10001;
	private final static int RC_WAITING_ROOM = 10002;
	private static final int MIN_PLAYERS = 1;
	private static final int MAX_PLAYERS = 2;
	private String incomingInvitationId;
	private GameListener gameListener;
	private NetworkListener networkListener;
	private String currentRoomId = null;
	private Intent previousMatch;
	private String currentPlayerID;
	private String currentOpponentID;


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("HEI1");
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new CastleCrush(this), config);
		System.out.println("HEI2");


		gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		gameHelper.enableDebugLog(false);

		GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener() {
			@Override
			public void onSignInFailed() {
				System.out.println("sign in failed");
			}

			@Override
			public void onSignInSucceeded() {
				Log.d(TAG, "onSignInSucceeded: ");
				if (gameHelper.hasInvitation()) {
					acceptInviteToRoom(gameHelper.getInvitationId());
				}
			}
		};

		gameHelper.setup(gameHelperListener);



	}
	@Override
	protected void onStart()
	{
		super.onStart();
		gameHelper.onStart(this);
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		gameHelper.onStop();
		leaveRoom();
		stopKeepingScreenOn();
	}

	@Override
	public void setGameListener(GameListener gameListener) {
		this.gameListener = gameListener;
	}

	@Override
	public void setNetworkListener(NetworkListener networkListener) {
		this.networkListener = networkListener;
	}

	public void toast(){
		Toast.makeText(this,"Welcome back " + gameHelper.getPlayerDisplayName(), Toast.LENGTH_LONG).show();
	}

	@Override
	public String getDisplayName() {
		if (!gameHelper.getApiClient().isConnected()) {
			return null;
		}
		return Games.Players.getCurrentPlayer(gameHelper.getApiClient()).getDisplayName();
	}


	////////////////////////////////KEEP SCREEN ON ///////////////////////////////////

	//prevent screen from sleeping during handshake
	void keepScreenOn() {
		this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	// Clears the flag that keeps the screen on.
	void stopKeepingScreenOn() {
		this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}



	////////////////////////SIGN IN/OUT METODER ////////////////////////////////////////////7
	@Override
	public void signIn() {
		try {
			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		}
		catch (Exception e) {
			Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void signOut() {
		try {
			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					gameHelper.signOut();
				}
			});
		}
		catch (Exception e) {
			Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public boolean isSignedIn() {
		return gameHelper.isSignedIn();
	}

	@Override
	public void quitGame() {
		if (!isSignedIn()) {
			return;
		}
		if (currentRoomId == null) {
			return;
		}
		leaveRoom();
	}

	void leaveRoom() {
		Log.d(TAG, "Leaving room.");
		stopKeepingScreenOn();
		if (currentRoomId != null) {
			Games.RealTimeMultiplayer.leave(gameHelper.getApiClient(), this, currentRoomId);
			currentRoomId = null;
			gameListener.goToMain();
		}
	}



	@Override
	public void startQuickGame() {
		final int MIN_OPPONENTS = 1, MAX_OPPONENTS = 1;
		Bundle autoMatchCriteria = RoomConfig.createAutoMatchCriteria(MIN_OPPONENTS, MAX_OPPONENTS, 0);

		RoomConfig.Builder rtmConfigBuilder = RoomConfig.builder((RoomUpdateListener) this);
		rtmConfigBuilder.setMessageReceivedListener((RealTimeMessageReceivedListener) this);
		rtmConfigBuilder.setRoomStatusUpdateListener((RoomStatusUpdateListener) this);
		rtmConfigBuilder.setAutoMatchCriteria(autoMatchCriteria);


		//prevent screen from sleeping
		keepScreenOn();

		Games.RealTimeMultiplayer.create(gameHelper.getApiClient(), rtmConfigBuilder.build());

	}




	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		gameHelper.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RC_WAITING_ROOM) {
			handleWaitingRoomResult(data, resultCode);
		}else if (requestCode == RC_SELECT_PLAYERS) {
			Log.d(TAG, "onActivityResult: RC_SELECT_PLAYERS");
			handleSelectPlayersResult(resultCode, data);
		}else if (requestCode == RC_INVITATION_INBOX){
			Log.d(TAG, "onActivityResult: RC_INVITATION_INBOX");
			handleInvitationInboxResult(resultCode, data);

		}
	}

	///////////////////////    SELECT OPPONENTS /////////////////////////////////////////

	@Override
	public void startSelectOpponents() {
		Intent intent = Games.RealTimeMultiplayer.getSelectOpponentsIntent(gameHelper.getApiClient(), MIN_PLAYERS, MAX_PLAYERS);
		this.startActivityForResult(intent, RC_SELECT_PLAYERS);
	}


	private void handleSelectPlayersResult(int response, Intent data) {
		Log.d(TAG, "handleSelectPlayersResult: ");
		if (response != Activity.RESULT_OK) {
			Log.w(TAG, "*** select players UI cancelled, " + response);
			return;
		}

		Log.d(TAG, "Select players UI succeeded.");

		// get the invitee list
		ArrayList<String> invite = data.getStringArrayListExtra(GamesClient.EXTRA_PLAYERS);
		Log.d(TAG, "Invitee count: " + invite.size());


		// create the room
		Log.d(TAG, "Creating room...");
		RoomConfig.Builder rtmConfigBuilder = RoomConfig.builder(this);
		rtmConfigBuilder.addPlayersToInvite(invite);
		rtmConfigBuilder.setMessageReceivedListener(this);
		rtmConfigBuilder.setRoomStatusUpdateListener(this);


		keepScreenOn();
		Games.RealTimeMultiplayer.create(gameHelper.getApiClient(), rtmConfigBuilder.build());
		Log.d(TAG, "Room created, waiting for it to be ready...");

		//Saves intent with selected players for rematch
		previousMatch = data;
	}


	/////////////////// WAITING ROOM /////////////////////////////

	private void handleWaitingRoomResult(Intent i, int resultCode) {
		Room room = i.getParcelableExtra(Multiplayer.EXTRA_ROOM);

		if (resultCode == Activity.RESULT_OK) {
			// Start the game!
			Log.d(TAG, "handleWaitingRoomResult: OK");
			System.out.println("handleWaitingRoomResult: OK");
			gameListener.onMultiplayerGameStarting();
			List<PlayerData> playerList = new ArrayList<>();
			currentPlayerID = Games.Players.getCurrentPlayerId(gameHelper.getApiClient());
			for (Participant participant : room.getParticipants()) {
				String playerId = participant.getPlayer().getPlayerId();
				PlayerData playerData = new PlayerData(playerId, participant.getParticipantId(), participant.getDisplayName());
				if (PlayerData.equals(currentPlayerID, playerId)) {
					playerData.isSelf = true;
				}else{
					currentOpponentID = playerId;
				}
				playerList.add(playerData);
			}
			networkListener.onRoomReady(playerList);

		} else if (resultCode == Activity.RESULT_CANCELED) {
			// Waiting room was dismissed with the back button. The meaning of this
			// action is up to the game. You may choose to leave the room and cancel the
			// match, or do something else like minimize the waiting room and
			// continue to connect in the background.
			Log.d(TAG, "handleWaitingRoomResult: CANCEL");
			System.out.println("handleWaitingRoomResult: CANCEL");
		} else if (resultCode == RESULT_LEFT_ROOM) {
			// player wants to leave the room.
			Log.d(TAG, "handleWaitingRoomResult: LEFT_ROOM");
			System.out.println("handleWaitingRoomResult: LEFT_ROOM");
			leaveRoom();
		}
	}

	private void showWaitingRoom(Room room) {

		Intent i = Games.RealTimeMultiplayer.getWaitingRoomIntent(gameHelper.getApiClient(),room,MAX_PLAYERS);
		this.startActivityForResult(i,RC_WAITING_ROOM);
	}

	//To dismiss the waiting room for the other players,
	// your game should send a reliable real-time message to the other players to indicate that the game is starting early.
	// When your game receives the message, it should dismiss the waiting room UI.

	boolean mWaitingRoomFinishedFromCode = false;

	private void onStartGameMessageReceived() {
		mWaitingRoomFinishedFromCode = true;
		finishActivity(RC_WAITING_ROOM);
	}


	//////////////////// INVITATION INBOX /////////////////////////////////////

	@Override
	public void showInvitationInbox() {
		Intent i = Games.Invitations.getInvitationInboxIntent(gameHelper.getApiClient());
		this.startActivityForResult(i,RC_INVITATION_INBOX);

	}

	private void handleInvitationInboxResult(int resultCode, Intent data) {
		Log.d(TAG, "handleInvitationInboxResult: ");
		if (resultCode != Activity.RESULT_OK) {
			// Canceled or some error.
			return;
		}
		Invitation invitation = data.getExtras().getParcelable(Multiplayer.EXTRA_INVITATION);
		if (invitation != null) {

			RoomConfig.Builder roomConfigBuilder = RoomConfig.builder(this);
			roomConfigBuilder.setInvitationIdToAccept(invitation.getInvitationId())
					.setMessageReceivedListener(this)
					.setRoomStatusUpdateListener(this);

			// prevent screen from sleeping during handshake
			keepScreenOn();

			Games.RealTimeMultiplayer.join(gameHelper.getApiClient(), roomConfigBuilder.build());
		}
	}

	@Override
	public void onInvitationReceived(Invitation invitation) {
		Log.d(TAG, "onInvitationReceived: ");
		incomingInvitationId = invitation.getInvitationId();
		Toast.makeText(this, invitation.getInviter().getDisplayName() + " has invited you. ", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onInvitationRemoved(String s) {
		Log.d(TAG, "onInvitationRemoved: ");
	}

	public void acceptInviteToRoom(String invId) {
		Log.d(TAG, "Accepting invitation: " + invId);
		RoomConfig.Builder roomConfigBuilder = RoomConfig.builder(this);
		roomConfigBuilder.setInvitationIdToAccept(invId)
				.setMessageReceivedListener(this)
				.setRoomStatusUpdateListener(this);
		keepScreenOn();
		Games.RealTimeMultiplayer.join(gameHelper.getApiClient(), roomConfigBuilder.build());
	}




   ////////////////////// ROOMLISTENER-METHODS /////////////////////////////

	@Override
	public void onRoomCreated(int code, @Nullable Room room) {
		// Update UI and internal state based on room updates.
		if (code == OK && room != null) {
			Log.d(TAG, "Room " + room.getRoomId() + " created.");
			currentRoomId = room.getRoomId();
			showWaitingRoom(room);
		} else {
			Log.w(TAG, "Error creating room: " + code);
			// let screen go to sleep
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		}
	}

	@Override
	public void onJoinedRoom(int code, Room room) {
		// Update UI and internal state based on room updates.
		if (code == OK && room != null) {
			Log.d(TAG, "Room " + room.getRoomId() + " joined.");
			currentRoomId = room.getRoomId();
			showWaitingRoom(room);
		} else {
			Log.w(TAG, "Error joining room: " + code);
			// let screen go to sleep
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}
	}

	@Override
	public void onLeftRoom(int code, @NonNull String roomId) {
		Log.d(TAG, "Left room" + roomId);
	}

	@Override
	public void onRoomConnected(int code, Room room) {
		if (code == OK && room != null) {
			Log.d(TAG, "Room " + room.getRoomId() + " connected.");
		} else {
			Log.w(TAG, "Error connecting to room: " + code);
			// let screen go to sleep
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		}
	}

	@Override
	public void onRoomConnecting(Room room) { Log.d(TAG, "onRoomConnecting");}

	@Override
	public void onRoomAutoMatching(Room room) {
		Log.d(TAG, "onRoomAutoMatching: ");
	}

	@Override
	public void onPeerInvitedToRoom(Room room, List<String> list) {
		Log.d(TAG, "onPeerInvitedToRoom: ");
	}

	@Override
	public void onPeerDeclined(Room room, List<String> list) {
		Log.d(TAG, "onPeerDeclined: ");
	}

	@Override
	public void onPeerJoined(Room room, List<String> list) {
		Log.d(TAG, "onPeerJoined: ");
	}

	@Override
	public void onPeerLeft(Room room, List<String> list) {
		Log.d(TAG, "onPeerLeft: ");
	}

	@Override
	public void onConnectedToRoom(Room room) {
		Log.d(TAG, "onConnectedToRoom: ");
		stopKeepingScreenOn();
		if (currentRoomId == null){
			currentRoomId = room.getRoomId();
		}
	}

	@Override
	public void onDisconnectedFromRoom(Room room) {
		Log.d(TAG, "onDisconnectedFromRoom: ");
		currentRoomId = null;
	}

	@Override
	public void onPeersConnected(Room room, List<String> list) {
		Log.d(TAG, "onPeersConnected: ");
	}

	@Override
	public void onPeersDisconnected(Room room, List<String> list) {
		Log.d(TAG, "onPeersDisconnected: ");
	}

	@Override
	public void onP2PConnected(String s) {
		Log.d(TAG, "onP2PConnected: ");
	}

	@Override
	public void onP2PDisconnected(String s) {
		Log.d(TAG, "onP2PDisconnected: ");
	}


	///////////////////////// REMATCH /////////////////////////////////

	@Override
	public void sendOutRematch(){
		handleSelectPlayersResult(Activity.RESULT_OK, previousMatch);
	}

	ArrayList<String> playerIDS = new ArrayList<>();
	@Override
	public void rematch(List<PlayerData> players){
		String currentPlayerId = Games.Players.getCurrentPlayerId(gameHelper.getApiClient());
		for (PlayerData p : players){
			if (!PlayerData.equals(currentPlayerId, p.getPlayerID())) {
				playerIDS.add(p.getPlayerID());
			}
		}

		Log.d(TAG, "Creating room...");
		RoomConfig.Builder rtmConfigBuilder = RoomConfig.builder(this);
		rtmConfigBuilder.addPlayersToInvite(playerIDS);
		rtmConfigBuilder.setMessageReceivedListener(this);
		rtmConfigBuilder.setRoomStatusUpdateListener(this);

		keepScreenOn();
		Games.RealTimeMultiplayer.create(gameHelper.getApiClient(), rtmConfigBuilder.build());
		Log.d(TAG, "Room created, waiting for it to be ready...");

		//Saves intent with selected players for rematch
		//previousMatch = data;
	}


	///////////////////////////// MESSAGES DURING GAME ////////////////////////

	@Override
	public void onRealTimeMessageReceived(RealTimeMessage realTimeMessage) {
		if (networkListener == null) {
			Gdx.app.debug(TAG, "onRealTimeMessageReceived: NetworkListener is null");
			return;
		}
		byte[] messageData = realTimeMessage.getMessageData();
		String senderParticipantId = realTimeMessage.getSenderParticipantId();
		int describeContents = realTimeMessage.describeContents();
		if (realTimeMessage.isReliable()) {
			networkListener.onReliableMessageReceived(senderParticipantId, describeContents, messageData);
		} else {
			networkListener.onUnreliableMessageReceived(senderParticipantId, describeContents, messageData);
		}
	}

	@Override
	public void sendUnreliableMessageToOthers(byte[] messageData) {
		if (currentRoomId == null) {return;}

		/////WHAT??
		if (gameHelper.isSignedIn()) {return;}
		Games.RealTimeMultiplayer.sendUnreliableMessageToAll(gameHelper.getApiClient(), messageData, currentRoomId);

	}

	public void sendReliableMessage(byte[] messageData){

		Games.RealTimeMultiplayer.sendReliableMessage(gameHelper.getApiClient(),handleMessageSentCallback, messageData,currentRoomId, currentOpponentID);
	}

	////// ????????????????? ///////
	HashSet<Integer> pendingMessageSet = new HashSet<>();
	synchronized void recordMessageToken(int tokenId) {
		pendingMessageSet.add(tokenId);
	}

	private RealTimeMultiplayer.ReliableMessageSentCallback handleMessageSentCallback = new RealTimeMultiplayer.ReliableMessageSentCallback() {
		@Override
		public void onRealTimeMessageSent(int statusCode, int tokenId, String recipientId) {
			// handle the message being sent.
			synchronized (this) {
				pendingMessageSet.remove(tokenId);
			}
		}
	};
}




