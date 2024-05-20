package com.novi.app.service.impl;

import com.novi.app.model.*;
import com.novi.app.model.repository.MusicInstrumentRepository;
import com.novi.app.model.repository.MusicStyleRepository;
import com.novi.app.model.request.CreateUserRequest;
import com.novi.app.model.request.ModifyUserRequest;
import com.novi.app.service.UserService;
import com.novi.app.util.Constants;
import com.novi.app.util.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.novi.app.model.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(
            UserServiceImpl.class
    );

    private final UserRepository userRepository;
    private final MusicInstrumentRepository musicInstrumentRepository;
    private final MusicStyleRepository musicStyleRepository;

    // maybe create manager due to many repo in constructor of service layout
    public UserServiceImpl(UserRepository userRepository,
                           MusicInstrumentRepository musicInstrumentRepository, MusicStyleRepository musicStyleRepository) {
        this.userRepository = userRepository;
        this.musicInstrumentRepository = musicInstrumentRepository;
        this.musicStyleRepository = musicStyleRepository;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    // TODO: looks like can be simplified
    @Override
    public Set<Group> getUserGroups(Long userId) {
        Optional<User> optionalUser = findUserById(userId);
        Set<Group> userGroups = new HashSet<>();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userGroups.addAll(user.getGroups());
        } else {
            logger.warn("WARN: No existing user with such id");
        }
        return userGroups;
    }

    @Override
    public Set<MusicStyle> getUserMusicStyles(Long userId) {
        Optional<User> optionalUser = findUserById(userId);
        Set<MusicStyle> musicStyles = new HashSet<>();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            musicStyles.addAll(user.getMusicStyles());
        } else {
            logger.warn("WARN: No existing user with such id");
        }
        return musicStyles;
    }

    @Override
    public Set<MusicInstrument> getUserMusicInstruments(Long userId) {
        Optional<User> optionalUser = findUserById(userId);
        Set<MusicInstrument> musicInstruments = new HashSet<>();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            musicInstruments.addAll(user.getMusicInstruments());
        } else {
            logger.warn("WARN: No existing user with such id");
        }
        return musicInstruments;
    }

    @Override
    public Set<Group> getGroupsOfLeader(Long userId) {
        Optional<User> optionalUser = findUserById(userId);
        Set<Group> groupsOfCurrentLeader = new HashSet<>();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            for (Group group : user.getGroups()) {
                if (user.getUserId() == group.getLeaderId()) {
                    groupsOfCurrentLeader.add(group);
                }
            }
        } else {
            logger.warn("WARN: No existing user with such id");
        }
        return groupsOfCurrentLeader;
    }

    @Override
    public User createUser(CreateUserRequest createUserRequest) {
        String firstName = createUserRequest.getFirstName();
        String lastName = createUserRequest.getLastName();
        String middleName = createUserRequest.getMiddleName();
        String phoneNumber = createUserRequest.getPhoneNumber();
        String email = createUserRequest.getEmail();
        String login = createUserRequest.getUserLogin();
        String password = createUserRequest.getPassword();
        String birthday = createUserRequest.getBirthday();
        User user = new User(firstName,
                lastName,
                middleName,
                phoneNumber,
                email,
                login,
                password,
                birthday);
        User savedUser = userRepository.save(user);
        String newUserRole = "USER_" + savedUser.getUserId();
        addUserRole(newUserRole, savedUser);
        logger.debug("user: {}", savedUser);
        return user;
    }

    @Override
    public Optional<User> updateUser(Long userId, ModifyUserRequest modifyUserRequest) {
        Optional<User> optionalUser = userRepository.findById(userId);
        logger.info("Incoming request: {}", modifyUserRequest);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // TODO: simplify
            if (!StringUtils.isEmpty(modifyUserRequest.getFirstName())) {
                user.setFirstName(modifyUserRequest.getFirstName());
            }
            if (!StringUtils.isEmpty(modifyUserRequest.getLastName())) {
                user.setLastName(modifyUserRequest.getLastName());
            }
            if (!StringUtils.isEmpty(modifyUserRequest.getMiddleName())) {
                user.setMiddleName(modifyUserRequest.getMiddleName());
            }
            if (!StringUtils.isEmpty(modifyUserRequest.getPhoneNumber())) {
                user.setPhoneNumber(modifyUserRequest.getPhoneNumber());
            }
            if (!StringUtils.isEmpty(modifyUserRequest.getBirthday())) {
                user.setBirthday(modifyUserRequest.getBirthday());
            }
            if (!StringUtils.isEmpty(modifyUserRequest.getEmail())) {
                user.setEmail(modifyUserRequest.getEmail());
            }
            // TODO: check unique login and throw error if needed
            if (!StringUtils.isEmpty(modifyUserRequest.getUserLogin())) {
                logger.info("Need to change login to the one from request: {}", modifyUserRequest.getUserLogin());
                user.setUserLogin(modifyUserRequest.getUserLogin());
            }
            if (!StringUtils.isEmpty(modifyUserRequest.getPassword())) {
                user.setPassword(UserUtil.encryptPassword(modifyUserRequest.getPassword()));
            }
            userRepository.save(user);
        } else {
            logger.warn("WARN: No existing user with such id");
        }
        return optionalUser;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void terminateUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User userToUpdate = optionalUser.get();
            userToUpdate.setDeletionDate(new Date());
            userRepository.save(userToUpdate);
        } else {
            logger.warn("WARN: No existing user with such id");
        }
    }

    public void addUserRole(String roleName, User user) {
        UserRole userRole = new UserRole();
        userRole.setRoleName(roleName);
        user.getUserRoles().add(userRole);
        logger.debug("roles of user {} with id {}", user.getUserRoles().stream().map(UserRole::getRoleName).collect(Collectors.toList()), user.getUserId());
        userRepository.save(user);
    }

    public void addMusicInstrument(Long userId, Integer instrumentId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("There is no user with such id, data is broken"));
        MusicInstrument instrument = musicInstrumentRepository.findById(instrumentId).orElseThrow(() -> new RuntimeException("There is no music instrument with such id, data is broken"));
        user.getMusicInstruments().add(instrument);
        logger.info("instruments of user: {}", user.getMusicInstruments().stream().map(MusicInstrument::getInstrumentName).collect(Collectors.toList()));
        userRepository.save(user);
    }

    public void addMusicStyle(Long userId, Integer styleId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("There is no user with such id, data is broken"));
        MusicStyle style = musicStyleRepository.findById(styleId).orElseThrow(() -> new RuntimeException("There is no music instrument with such id, data is broken"));
        user.getMusicStyles().add(style);
        logger.info("styles of user: {}", user.getMusicStyles().stream().map(MusicStyle::getStyleName).collect(Collectors.toList()));
        userRepository.save(user);
    }

    @Override
    public List<User> findNewlyCreatedUsers() {
        Calendar currentDate = new GregorianCalendar();
        currentDate.add(Calendar.MONTH, -1);
        return userRepository.findNewlyCreatedUsers(currentDate.getTime());
    }

    @Override
    public List<User> findActiveUsers() {
        return userRepository.findActiveUsers(new Date(Constants.MAX_DATE));
    }

    // TODO: Users in groups automatically inherit music style of group
    @Override
    public List<User> getUsersWithCurrentMusicStyle(int styleId) {
        return userRepository
                .findAll()
                .stream()
                .filter(user -> !(user.getMusicStyles()
                        .stream()
                        .filter(musicStyle -> musicStyle.getStyleId() == styleId)
                        .toList().isEmpty()))
                .toList();
    }

    @Override
    public UserDetails loadUserByUsername(String userLogin) throws
            UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserLogin(userLogin);
        org.springframework.security.core.userdetails.User.UserBuilder builder;
        if (user.isPresent()) {
            User currentUser = user.get();
            builder = org.springframework.security.core.userdetails.
                    User.withUsername(userLogin);
            builder.password(currentUser.getPassword());
            builder.roles(currentUser.getUserRoles().stream().map(UserRole::getRoleName).toArray(String[]::new));
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
        return builder.build();
    }
}