package service;

import dao.AlumniDAO;
import dao.UserDAO;
import java.util.List;
import java.util.Map;

public class AdminService {

    private final UserDAO userDAO = new UserDAO();
    private final AlumniDAO alumniDAO = new AlumniDAO();

    public List<Map<String, Object>> viewAllUsers() {
        return userDAO.getAllUsers();
    }

    public List<String[]> viewPendingAlumni() {
        return alumniDAO.getPendingAlumni();
    }

    public boolean verifyAlumni(int alumniUserId) {
        boolean profileUpdated =
                alumniDAO.updateVerificationStatus(
                        alumniUserId, "VERIFIED");

        boolean accountUpdated =
                userDAO.updateUserStatus(
                        alumniUserId, "ACTIVE");

        return profileUpdated && accountUpdated;
    }

    public boolean rejectAlumni(int alumniUserId) {
        boolean profileUpdated =
                alumniDAO.updateVerificationStatus(
                        alumniUserId, "REJECTED");

        // The users table allows BLOCKED, not REJECTED.
        boolean accountUpdated =
                userDAO.updateUserStatus(
                        alumniUserId, "BLOCKED");

        return profileUpdated && accountUpdated;
    }

    public boolean changeUserStatus(int userId, String status) {
        return userDAO.updateUserStatus(userId, status);
    }
}