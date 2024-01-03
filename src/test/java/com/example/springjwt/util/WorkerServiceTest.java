//package com.example.springjwt.util;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//
//@ExtendWith(MockitoExtension.class)
//class WorkerServiceTest {
//
//    @InjectMocks
//    private WorkerService workerService;
//
//    @Mock
//    private WorkRepository workRepository;
//
//    @Test
//    void sumOfSalaries() {
//        Worker worker = new Worker();
//        worker.setSalaries(1);
//
//        Worker worker1 = new Worker();
//        worker1.setSalaries(3);
//
//        Mockito.when(workRepository.getWorkers()).thenReturn(List.of(worker, worker1));
//
//        int sumOfSalaries = workerService.getSumOfSalaries();
//        Assertions.assertEquals(4, sumOfSalaries);
//    }
//}
