package com.ticketbooking.show;

import com.ticketbooking.show.dto.SeatResponse;
import com.ticketbooking.show.dto.SeatType;
import com.ticketbooking.show.dto.ShowRequest;
import com.ticketbooking.show.dto.ShowResponse;
import com.ticketbooking.show.entity.Show;
import com.ticketbooking.show.feignClient.TheaterClient;
import com.ticketbooking.show.mapper.ShowMapper;
import com.ticketbooking.show.repository.ShowRepository;
import com.ticketbooking.show.repository.ShowSeatRepository;
import com.ticketbooking.show.repository.ShowStatus;
import com.ticketbooking.show.service.ShowService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @Mock
    ShowRepository showRepository;
    @Mock
    ShowSeatRepository showSeatRepository;
    @Mock
    ShowMapper showMapper;
    @Mock
    TheaterClient theaterClient;

    @InjectMocks
    ShowService showService;


    @Test
    public void ShowService_CreateShow_ShowResponse(){
        Show show = new Show();
        when(showMapper.toEntity(any(ShowRequest.class))).thenReturn(show);
        when(showRepository.save(any(Show.class))).thenReturn(show);
        SeatResponse seat1 = new SeatResponse();
        seat1.setId(100L);
        seat1.setSeatPosition("A1");
        seat1.setSeatType(SeatType.REGULAR);
        SeatResponse seat2 = new SeatResponse();
        seat2.setId(101L);
        seat2.setSeatPosition("A2");
        seat2.setSeatType(SeatType.PREMIUM);
        when(theaterClient.getSeatsByScreenId(1L)).thenReturn(List.of(seat1,seat2));
        when(showSeatRepository.saveAll(any())).thenReturn(List.of());
        when(showMapper.toResponse(any(Show.class))).thenReturn(new ShowResponse());
        ShowRequest request = new ShowRequest();
        request.setScreenId(1L);
        request.setMovieId(10L);
        ShowResponse showResponse = showService.createShow(request);
        assertThat(showResponse).isNotNull();
    }
}
