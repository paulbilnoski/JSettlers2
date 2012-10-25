package soc.message;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * A {@link MessageFactory} implementation that uses the "classic SOC" serialized format of strings
 * with separators.
 */
public final class SOCClassicMessageFactory implements MessageFactory
{
    public NetworkMessage createMessage(String messageStr) throws MessageException
    {
        StringTokenizer st = new StringTokenizer(messageStr, SOCMessage.sep);

        // get the id that identifies the type of message
        int msgId = Integer.parseInt(st.nextToken());

        // get the rest of the data
        String data = null;

        /**
         * to handle {@link SOCMessageMulti} subclasses -
         * multiple parameters with sub-fields.
         * If only one param is seen, this will be null;
         * use {@link #toSingleElemArray(String)} to build it.
         *<P>
         * Note that if you passed a non-null gamename to the
         * {@link SOCMessageTemplateMs} or {@link SOCMessageTemplateMi} constructor,
         * then multiData[0] here will be gamename,
         * and multiData[1] == param[0] as passed to that constructor.
         *<code>
         *     case POTENTIALSETTLEMENTS:
         *         if (multiData == null)
         *             multiData = toSingleElemArray(data);
         *         return SOCPotentialSettlements.parseDataStr(multiData);
         *</code>
         */
        List<String> multiData = null;

        try
        {
            data = st.nextToken();
            while (st.hasMoreTokens())
            {
                // test for a null list instead of testing for hasMoreTokens outside and inside the loop
                if (multiData == null)
                    multiData = new ArrayList<String>();
                
                multiData.add(st.nextToken());
            }
        }
        catch (NoSuchElementException e)
        {
            data = "";
        }
        
        try
        {
            return createMessage(msgId, data, multiData == null ? null : multiData.toArray(new String[multiData.size()]));
        }
        catch (Exception e)
        {
            throw new MessageException("Failed creating message for message id ["+ msgId+ "] message: ["+messageStr+"]", e);
        }
    }

    /**
     * convert the data part and create the message
     */
    private SOCMessage createMessage(int msgId, String data, String[] multiData) throws Exception
    {
        switch (msgId)
        {
        case SOCMessage.NULLMESSAGE:
            return null;

        case SOCMessage.NEWCHANNEL:
            return SOCNewChannel.parseDataStr(data);

        case SOCMessage.MEMBERS:
            return SOCMembers.parseDataStr(data);

        case SOCMessage.CHANNELS:
            return SOCChannels.parseDataStr(data);

        case SOCMessage.JOIN:
            return SOCJoin.parseDataStr(data);

        case SOCMessage.TEXTMSG:
            return SOCTextMsg.parseDataStr(data);

        case SOCMessage.LEAVE:
            return SOCLeave.parseDataStr(data);

        case SOCMessage.DELETECHANNEL:
            return SOCDeleteChannel.parseDataStr(data);

        case SOCMessage.LEAVEALL:
            return SOCLeaveAll.parseDataStr(data);

        case SOCMessage.PUTPIECE:
            return SOCPutPiece.parseDataStr(data);

        case SOCMessage.GAMETEXTMSG:
            return SOCGameTextMsg.parseDataStr(data);

        case SOCMessage.LEAVEGAME:
            return SOCLeaveGame.parseDataStr(data);

        case SOCMessage.SITDOWN:
            return SOCSitDown.parseDataStr(data);

        case SOCMessage.JOINGAME:
            return SOCJoinGame.parseDataStr(data);

        case SOCMessage.BOARDLAYOUT:
            return SOCBoardLayout.parseDataStr(data);

        case SOCMessage.GAMES:
            return SOCGames.parseDataStr(data);

        case SOCMessage.DELETEGAME:
            return SOCDeleteGame.parseDataStr(data);

        case SOCMessage.NEWGAME:
            return SOCNewGame.parseDataStr(data);

        case SOCMessage.GAMEMEMBERS:
            return SOCGameMembers.parseDataStr(data);

        case SOCMessage.STARTGAME:
            return SOCStartGame.parseDataStr(data);

        case SOCMessage.JOINAUTH:
            return SOCJoinAuth.parseDataStr(data);

        case SOCMessage.JOINGAMEAUTH:
            return SOCJoinGameAuth.parseDataStr(data);

        case SOCMessage.IMAROBOT:
            return SOCImARobot.parseDataStr(data);

        case SOCMessage.JOINGAMEREQUEST:
            return SOCJoinGameRequest.parseDataStr(data);

        case SOCMessage.PLAYERELEMENT:
            return SOCPlayerElement.parseDataStr(data);

        case SOCMessage.GAMESTATE:
            return SOCGameState.parseDataStr(data);

        case SOCMessage.TURN:
            return SOCTurn.parseDataStr(data);

        case SOCMessage.SETUPDONE:
            return SOCSetupDone.parseDataStr(data);

        case SOCMessage.DICERESULT:
            return SOCDiceResult.parseDataStr(data);

        case SOCMessage.DISCARDREQUEST:
            return SOCDiscardRequest.parseDataStr(data);

        case SOCMessage.ROLLDICEREQUEST:
            return SOCRollDiceRequest.parseDataStr(data);

        case SOCMessage.ROLLDICE:
            return SOCRollDice.parseDataStr(data);

        case SOCMessage.ENDTURN:
            return SOCEndTurn.parseDataStr(data);

        case SOCMessage.DISCARD:
            return SOCDiscard.parseDataStr(data);

        case SOCMessage.MOVEROBBER:
            return SOCMoveRobber.parseDataStr(data);

        case SOCMessage.CHOOSEPLAYER:
            return SOCChoosePlayer.parseDataStr(data);

        case SOCMessage.CHOOSEPLAYERREQUEST:
            return SOCChoosePlayerRequest.parseDataStr(data);

        case SOCMessage.REJECTOFFER:
            return SOCRejectOffer.parseDataStr(data);

        case SOCMessage.CLEAROFFER:
            return SOCClearOffer.parseDataStr(data);

        case SOCMessage.ACCEPTOFFER:
            return SOCAcceptOffer.parseDataStr(data);

        case SOCMessage.BANKTRADE:
            return SOCBankTrade.parseDataStr(data);

        case SOCMessage.MAKEOFFER:
            return SOCMakeOffer.parseDataStr(data);

        case SOCMessage.CLEARTRADEMSG:
            return SOCClearTradeMsg.parseDataStr(data);

        case SOCMessage.BUILDREQUEST:
            return SOCBuildRequest.parseDataStr(data);

        case SOCMessage.CANCELBUILDREQUEST:
            return SOCCancelBuildRequest.parseDataStr(data);

        case SOCMessage.BUYCARDREQUEST:
            return SOCBuyCardRequest.parseDataStr(data);

        case SOCMessage.DEVCARD:
            return SOCDevCard.parseDataStr(data);

        case SOCMessage.DEVCARDCOUNT:
            return SOCDevCardCount.parseDataStr(data);

        case SOCMessage.SETPLAYEDDEVCARD:
            return SOCSetPlayedDevCard.parseDataStr(data);

        case SOCMessage.PLAYDEVCARDREQUEST:
            return SOCPlayDevCardRequest.parseDataStr(data);

        case SOCMessage.DISCOVERYPICK:
            return SOCDiscoveryPick.parseDataStr(data);

        case SOCMessage.MONOPOLYPICK:
            return SOCMonopolyPick.parseDataStr(data);

        case SOCMessage.FIRSTPLAYER:
            return SOCFirstPlayer.parseDataStr(data);

        case SOCMessage.SETTURN:
            return SOCSetTurn.parseDataStr(data);

        case SOCMessage.ROBOTDISMISS:
            return SOCRobotDismiss.parseDataStr(data);

        case SOCMessage.POTENTIALSETTLEMENTS:
            return SOCPotentialSettlements.parseDataStr(data);

        case SOCMessage.CHANGEFACE:
            return SOCChangeFace.parseDataStr(data);

        case SOCMessage.REJECTCONNECTION:
            return SOCRejectConnection.parseDataStr(data);

        case SOCMessage.LASTSETTLEMENT:
            return SOCLastSettlement.parseDataStr(data);

        case SOCMessage.GAMESTATS:
            return SOCGameStats.parseDataStr(data);

        case SOCMessage.BCASTTEXTMSG:
            return SOCBCastTextMsg.parseDataStr(data);

        case SOCMessage.RESOURCECOUNT:
            return SOCResourceCount.parseDataStr(data);

        case SOCMessage.ADMINPING:
            return SOCAdminPing.parseDataStr(data);

        case SOCMessage.ADMINRESET:
            return SOCAdminReset.parseDataStr(data);

        case SOCMessage.LONGESTROAD:
            return SOCLongestRoad.parseDataStr(data);

        case SOCMessage.LARGESTARMY:
            return SOCLargestArmy.parseDataStr(data);

        case SOCMessage.SETSEATLOCK:
            return SOCSetSeatLock.parseDataStr(data);

        case SOCMessage.STATUSMESSAGE:
            return SOCStatusMessage.parseDataStr(data);

        case SOCMessage.CREATEACCOUNT:
            return SOCCreateAccount.parseDataStr(data);

        case SOCMessage.UPDATEROBOTPARAMS:
            return SOCUpdateRobotParams.parseDataStr(data);

        case SOCMessage.SERVERPING:
            return SOCServerPing.parseDataStr(data);

        case SOCMessage.ROLLDICEPROMPT:     // autoroll, 20071003, sf patch #1812254
            return SOCRollDicePrompt.parseDataStr(data);

        case SOCMessage.RESETBOARDREQUEST:  // resetboard, 20080217, v1.1.00
            return SOCResetBoardRequest.parseDataStr(data);

        case SOCMessage.RESETBOARDAUTH:     // resetboard, 20080217, v1.1.00
            return SOCResetBoardAuth.parseDataStr(data);

        case SOCMessage.RESETBOARDVOTEREQUEST:  // resetboard, 20080223, v1.1.00
            return SOCResetBoardVoteRequest.parseDataStr(data);

        case SOCMessage.RESETBOARDVOTE:     // resetboard, 20080223, v1.1.00
            return SOCResetBoardVote.parseDataStr(data);

        case SOCMessage.RESETBOARDREJECT:   // resetboard, 20080223, v1.1.00
            return SOCResetBoardReject.parseDataStr(data);

        case SOCMessage.VERSION:            // cli-serv versioning, 20080807, v1.1.00
            return SOCVersion.parseDataStr(data);

        case SOCMessage.NEWGAMEWITHOPTIONS:     // per-game options, 20090601, v1.1.07
            return SOCNewGameWithOptions.parseDataStr(data);

        case SOCMessage.NEWGAMEWITHOPTIONSREQUEST:  // per-game options, 20090601, v1.1.07
            return SOCNewGameWithOptionsRequest.parseDataStr(data);

        case SOCMessage.GAMEOPTIONGETDEFAULTS:  // per-game options, 20090601, v1.1.07
            return SOCGameOptionGetDefaults.parseDataStr(data);

        case SOCMessage.GAMEOPTIONGETINFOS:     // per-game options, 20090601, v1.1.07
            return SOCGameOptionGetInfos.parseDataStr(data);

        case SOCMessage.GAMEOPTIONINFO:         // per-game options, 20090601, v1.1.07
            return SOCGameOptionInfo.parseDataStr(multiData);

        case SOCMessage.GAMESWITHOPTIONS:       // per-game options, 20090601, v1.1.07
            return SOCGamesWithOptions.parseDataStr(multiData);

        case SOCMessage.BOARDLAYOUT2:      // 6-player board, 20091104, v1.1.08
            return SOCBoardLayout2.parseDataStr(data);

        case SOCMessage.PLAYERSTATS:       // per-player statistics, 20100312, v1.1.09
            return SOCPlayerStats.parseDataStr(multiData);

        case SOCMessage.PLAYERELEMENTS:    // multiple PLAYERELEMENT, 20100313, v1.1.09
            return SOCPlayerElements.parseDataStr(multiData);

        case SOCMessage.DEBUGFREEPLACE:    // debug piece Free Placement, 20110104, v1.1.12
            return SOCDebugFreePlace.parseDataStr(data);

        case SOCMessage.TIMINGPING:        // robot timing ping, 20111011, v1.1.13
            return SOCTimingPing.parseDataStr(data);

        case SOCMessage.MOVEPIECEREQUEST:  // move piece request, 20111203, v2.0.00
            return SOCMovePieceRequest.parseDataStr(data);

        case SOCMessage.MOVEPIECE:         // move piece announcement, 20111203, v2.0.00
            return SOCMovePiece.parseDataStr(data);

        case SOCMessage.PICKRESOURCESREQUEST:  // gold hex resources, 20120112, v2.0.00
            return SOCPickResourcesRequest.parseDataStr(data);

        case SOCMessage.PICKRESOURCES:     // gold hex resources, 20120112, v2.0.00
            return SOCPickResources.parseDataStr(data);

        default:
            throw new MessageException("Unhandled message type in SOCMessage.toMsg: " + msgId);
        }
    }
}
