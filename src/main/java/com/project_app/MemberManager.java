
package main.java.com.project_app;

import java.util.ArrayList;
import java.util.List;

public class MemberManager {
    private static List<Member> members = new ArrayList<>();

    public static boolean isUsernameAvailable(String username) {
        for (Member member : members) {
            if (member.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    public static void addMember(Member member) {
        members.add(member);
    }

    public static Member getMember(String username) {
        for (Member member : members) {
            if (member.getUsername().equals(username)) {
                return member;
            }
        }
        return null;
    }



    public static void updateMember(Member updatedMember) {
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getUsername().equals(updatedMember.getUsername())) {
                members.set(i, updatedMember);
                return;
            }
        }
    }
    public static void sendSongRecommendation(Member sender, Member receiver, Song song) {
        Notification notification = new Notification(sender.getUsername(), receiver.getUsername(), song);
        receiver.addNotification(notification);
        updateMember(receiver); // Mettre à jour le membre récepteur avec les nouvelles notifications
    }



}
